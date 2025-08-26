import {
  Component,
  ElementRef,
  OnInit,
  Renderer2,
  ViewChild,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { OrderList } from './order-list/order-list';

@Component({
  selector: 'app-order',
  imports: [OrderList, FormsModule],
  templateUrl: './order.html',
  styleUrl: './order.scss',
})
export class Order implements OnInit {
  @ViewChild(OrderList) OrderList!: OrderList;
  @ViewChild('formWrapper', { static: true }) formWrapper!: ElementRef;
  @ViewChild('overlayElement', { static: true }) overlayElement!: ElementRef;

  searchTerm: string = '';
  pageSizeInput: number = 5;

  constructor(private renderer: Renderer2) {}

  ngOnInit(): void {
    const overlayElement = document.querySelector('.bg-overlay') as HTMLElement;
    this.renderer.removeClass(overlayElement, 'overlay-hide');
    this.renderer.removeClass(overlayElement, 'overlay-show');
  }
}
