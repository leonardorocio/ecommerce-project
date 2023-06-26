import {
  Component,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Comment } from 'src/app/models/comment';
import { Product } from 'src/app/models/product';
import { User } from 'src/app/models/user';
import { AlertService } from 'src/app/services/alert.service';
import { CommentService } from 'src/app/services/comment.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css'],
})
export class CommentComponent implements OnInit {
  @Input() product!: Product;
  @Input() user!: User;
  comments!: Comment[];
  editing: boolean = false;
  currentText: string = '';
  currentRating: number = 1;
  editedCommentId!: number;
  editedCommentIndex!: number;

  constructor(
    private commentService: CommentService,
    private toastr: ToastrService,
    private alert: AlertService
  ) {}

  ngOnInit(): void {
    this.commentService
      .getCommentsFromProduct(this.product.productId)
      .subscribe((comments) => {
        const compareId = (commentA: Comment, commentB: Comment) =>
          commentA.userOwner.userId === this.user.userId ? 1 : 0;
        this.comments = comments.sort(compareId);
      });
  }

  clearComment() {
    this.currentRating = 1;
    this.currentText = '';
  }

  sendComment(text: string, rating: string) {
    if (this.editing) {
      const newComment = {
        commentId: this.editedCommentId,
        text: this.currentText,
        rating: this.currentRating,
        user_owner: this.user.userId,
        product_rated: this.product.productId,
      };
      this.commentService.editComment(newComment).subscribe((comment) => {
        this.comments[this.editedCommentIndex] = comment;
        this.editing = false;
        this.toastr.success('Comentário editado com sucesso', 'OK');
      });
    } else {
      this.commentService
        .createComment(
          text,
          Number.parseInt(rating),
          this.user.userId,
          this.product.productId
        )
        .subscribe((comment) => {
          this.comments.unshift(comment);
          this.toastr.success('Comentário criado com sucesso', 'OK');
        });
    }
    this.clearComment();
  }

  editComment(comment: Comment, index: number) {
    this.editing = true;
    this.editedCommentId = comment.commentId;
    this.currentRating = comment.rating;
    this.currentText = comment.text;
    this.editedCommentIndex = index;
  }

  deleteComment(commentId: number) {
    // Swal.fire({
    //   title: 'Cuidado!',
    //   text: 'Deseja excluir esse comentário?',
    //   icon: 'warning',
    //   showCancelButton: true,
    //   confirmButtonColor: '#ff0000',
    //   confirmButtonText: 'Confirmar',
    // })
    this.alert
      .warning('Cuidado', 'Deseja excluir esse comentário?')
      .then((result) => {
        if (result) {
          this.commentService.deleteComment(commentId).subscribe(() => {
            this.comments = this.comments.filter(
              (comment) => commentId !== comment.commentId
            );
            this.toastr.success('Comentário excluído com sucesso', 'OK');
            this.clearComment();
          });
        }
      });
  }

  cancelEditing() {
    this.editing = false;
    this.currentRating = 1;
    this.currentText = '';
  }
}
