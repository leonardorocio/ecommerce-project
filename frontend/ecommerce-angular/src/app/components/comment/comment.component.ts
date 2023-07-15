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
  @Input() user?: User;
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
      .getCommentsByProduct(this.product.id)
      .subscribe((comments) => {
        const compareId = (commentA: Comment, commentB: Comment) =>
          commentA.userOwner.id === this.user?.id ? 1 : 0;
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
        user_owner: this.user?.id,
        product_rated: this.product.id,
      };
      this.commentService
        .updateComment(newComment, this.editedCommentId)
        .subscribe((comment) => {
          this.comments[this.editedCommentIndex] = comment;
          this.editing = false;
          this.toastr.success('Comentário editado com sucesso', 'OK');
        });
    } else {
      const comment = {
        text: text,
        rating: Number.parseInt(rating),
        user_owner: this.user?.id,
        product_rated: this.product.id,
      };
      this.commentService.postComment(comment).subscribe((comment) => {
        this.comments.unshift(comment);
        this.toastr.success('Comentário criado com sucesso', 'OK');
      });
    }
    this.clearComment();
  }

  editComment(comment: Comment, index: number) {
    this.editing = true;
    this.editedCommentId = comment.id;
    this.currentRating = comment.rating;
    this.currentText = comment.text;
    this.editedCommentIndex = index;
  }

  deleteComment(commentId: number) {
    this.alert
      .warning('Cuidado', 'Deseja excluir esse comentário?')
      .then((result) => {
        if (result) {
          this.commentService.deleteComment(commentId).subscribe(() => {
            this.comments = this.comments.filter(
              (comment) => commentId !== comment.id
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
