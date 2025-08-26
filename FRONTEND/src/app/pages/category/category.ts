import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CategoryAddEdit } from './category-add-edit/category-add-edit';
import { CategoryList } from './category-list/category-list';

@Component({
  selector: 'app-category',
  imports: [CategoryList, CategoryAddEdit, FormsModule],
  templateUrl: './category.html',
  styleUrl: './category.scss'
})
export class Category implements OnInit {
  @ViewChild(CategoryList) categoryList!: CategoryList;
  @ViewChild('formWrapper', { static: true }) formWrapper!: ElementRef;
  @ViewChild('overlayElement', { static: true }) overlayElement!: ElementRef;

  selectedCategory: any = null;
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

  onCategorySelected(category: any): void {
    this.selectedCategory = category;
    this.openDrawerView();
  }

  onCategorySaved(): void {
    this.closeDrawerView();
    this.categoryList.loadCategories();
    this.selectedCategory = null;
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

    this.categoryList.selectedRowId = null;
    this.selectedCategory = null;

    this.renderer.removeClass(formWrapper, 'drawer-open');
    this.renderer.addClass(formWrapper, 'drawer-close');
    this.renderer.addClass(overlayElement, 'overlay-hide');
    this.renderer.removeClass(overlayElement, 'overlay-show');
  }

  resetForm(): void {
    this.selectedCategory = null;
  }
}
