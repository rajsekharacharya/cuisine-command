import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserAddEdit } from './user-add-edit/user-add-edit';
import { UserList } from './user-list/user-list';
@Component({
  selector: 'app-user',
  imports: [UserList, UserAddEdit, FormsModule],
  templateUrl: './user.html',
  styleUrl: './user.scss'
})
export class User implements OnInit {
  @ViewChild(UserList) userList!: UserList;
  @ViewChild('userAddEditModal') userAddEditModal!: ElementRef;
  @ViewChild('formWrapper', { static: true }) formWrapper!: ElementRef;
  @ViewChild('overlayElement', { static: true }) overlayElement!: ElementRef;

  selectedUser: any = null;
  selectedRowId: number | null = null;

  searchTerm: string = '';
  pageSizeInput: number = 5;

  constructor(
    private renderer: Renderer2
  ) { }

  ngOnInit(): void {
    const overlayElement = document.querySelector('.bg-overlay') as HTMLElement;
    this.renderer.removeClass(overlayElement, 'overlay-hide');
    this.renderer.removeClass(overlayElement, 'overlay-show');
  }

  onUserSelected(user: any): void {
    this.selectedUser = user;
    this.openDrawerView()
  }


  onUserSaved(): void {

    this.closeDrawerView();

    this.userList.loadUsers();
    this.selectedUser = null;
  }

  openDrawerView() {
    const formWrapper = this.formWrapper.nativeElement;
    const overlayElement = document.querySelector('.bg-overlay') as HTMLElement;
    this.renderer.removeClass(formWrapper, 'drawer-close');
    this.renderer.addClass(formWrapper, 'drawer-open');
    this.renderer.removeClass(overlayElement, 'overlay-hide');
    this.renderer.addClass(overlayElement, 'overlay-show');
  }

  closeDrawerView() {
    const formWrapper = this.formWrapper.nativeElement;
    const overlayElement = document.querySelector('.bg-overlay') as HTMLElement;

    this.userList.selectedRowId = null;
    this.selectedUser = null;

    this.renderer.removeClass(formWrapper, 'drawer-open');
    this.renderer.addClass(formWrapper, 'drawer-close');
    this.renderer.addClass(overlayElement, 'overlay-hide');
    this.renderer.removeClass(overlayElement, 'overlay-show');
  }

  resetForm(): void {
    this.selectedUser = null;
  }
}
