import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AddonsAddEdit } from './addons-add-edit/addons-add-edit';
import { AddonsList } from './addons-list/addons-list';

@Component({
  selector: 'app-addons',
  imports: [AddonsList, AddonsAddEdit, FormsModule],
  templateUrl: './addons.html',
  styleUrl: './addons.scss'
})
export class Addons implements OnInit {
  @ViewChild(AddonsList) addonsList!: AddonsList;
  @ViewChild('formWrapper', { static: true }) formWrapper!: ElementRef;
  @ViewChild('overlayElement', { static: true }) overlayElement!: ElementRef;

  selectedAddons: any = null;
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

  onAddonsSelected(addons: any): void {
    this.selectedAddons = addons;
    this.openDrawerView();
  }

  onAddonsSaved(): void {
    this.closeDrawerView();
    this.addonsList.loadAddons();
    this.selectedAddons = null;
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

    this.addonsList.selectedRowId = null;
    this.selectedAddons = null;

    this.renderer.removeClass(formWrapper, 'drawer-open');
    this.renderer.addClass(formWrapper, 'drawer-close');
    this.renderer.addClass(overlayElement, 'overlay-hide');
    this.renderer.removeClass(overlayElement, 'overlay-show');
  }

  resetForm(): void {
    this.selectedAddons = null;
  }
}
