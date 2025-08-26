import { Directive, ElementRef, Renderer2, OnInit, OnDestroy, inject } from '@angular/core';

@Directive({
  selector: '[appTeleportModal]',
  standalone: true
})
export class TeleportModalDirective implements OnInit, OnDestroy {
  private readonly modalElement: HTMLElement;
  private readonly renderer = inject(Renderer2);

  constructor(private readonly el: ElementRef) {
    this.modalElement = this.el.nativeElement;
  }

  ngOnInit(): void {
    this.renderer.appendChild(document.body, this.modalElement);
  }

  ngOnDestroy(): void {
    this.renderer.removeChild(document.body, this.modalElement);
  }
}
