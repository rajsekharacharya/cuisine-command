import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { VariationAddEdit } from './variation-add-edit/variation-add-edit';
import { VariationList } from './variation-list/variation-list';

@Component({
  selector: 'app-variation',
  imports: [VariationList, VariationAddEdit, FormsModule],
  templateUrl: './variation.html',
  styleUrl: './variation.scss'
})
export class Variation implements OnInit {
  @ViewChild(VariationList) variationList!: VariationList;
  @ViewChild('formWrapper', { static: true }) formWrapper!: ElementRef;
  @ViewChild('overlayElement', { static: true }) overlayElement!: ElementRef;

  selectedVariation: any = null;
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

  onVariationSelected(variation: any): void {
    this.selectedVariation = variation;
    this.openDrawerView();
  }

  onVariationSaved(): void {
    this.closeDrawerView();
    this.variationList.loadVariations();
    this.selectedVariation = null;
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

    this.variationList.selectedRowId = null;
    this.selectedVariation = null;

    this.renderer.removeClass(formWrapper, 'drawer-open');
    this.renderer.addClass(formWrapper, 'drawer-close');
    this.renderer.addClass(overlayElement, 'overlay-hide');
    this.renderer.removeClass(overlayElement, 'overlay-show');
  }

  resetForm(): void {
    this.selectedVariation = null;
  }
}
