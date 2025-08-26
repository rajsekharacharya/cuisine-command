import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RestaurantAddEdit } from './restaurant-add-edit/restaurant-add-edit';
import { RestaurantList } from './restaurant-list/restaurant-list';

@Component({
  selector: 'app-restaurant',
  imports: [RestaurantList, RestaurantAddEdit, FormsModule],
  templateUrl: './restaurant.html',
  styleUrl: './restaurant.scss'
})
export class Restaurant implements OnInit {
  @ViewChild(RestaurantList) restaurantList!: RestaurantList;
  @ViewChild('formWrapper', { static: true }) formWrapper!: ElementRef;
  @ViewChild('overlayElement', { static: true }) overlayElement!: ElementRef;

  selectedRestaurant: any = null;
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

  onRestaurantSelected(restaurant: any): void {
    this.selectedRestaurant = restaurant;
    this.openDrawerView();
  }

  onRestaurantSaved(): void {
    this.closeDrawerView();
    this.restaurantList.loadRestaurants();
    this.selectedRestaurant = null;
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

    this.restaurantList.selectedRowId = null;
    this.selectedRestaurant = null;

    this.renderer.removeClass(formWrapper, 'drawer-open');
    this.renderer.addClass(formWrapper, 'drawer-close');
    this.renderer.addClass(overlayElement, 'overlay-hide');
    this.renderer.removeClass(overlayElement, 'overlay-show');
  }

  resetForm(): void {
    this.selectedRestaurant = null;
  }
}
