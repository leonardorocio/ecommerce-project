import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DropdownService {

  constructor() { }

  showDropdown: boolean = false;

  showDropdownMenu(option: boolean) {
    this.showDropdown = option;
  }

}
