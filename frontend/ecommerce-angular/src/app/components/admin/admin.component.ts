import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { APIDocs, Tag, IMethod } from 'src/app/models/admin';
import { ApiDocsService } from 'src/app/services/api-docs.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
})
export class AdminComponent implements OnInit {
  constructor(private apiDocs: ApiDocsService, private router: Router) {}

  docs!: APIDocs;
  tags!: Tag[];
  paths!: IMethod[];

  ngOnInit(): void {
    this.apiDocs.getAPIDocs().subscribe((docs) => {
      this.docs = docs;
      this.tags = this.docs.tags;
    });
  }

  sendToTagEntity(tag: string) {
    this.router.navigateByUrl(`/admin/${tag}`, { state: { docs: this.docs } });
  }
}
