// calculator-overlay.service.ts
import { Injectable, Injector } from '@angular/core';
import { Overlay, OverlayRef } from '@angular/cdk/overlay';
import { ComponentPortal } from '@angular/cdk/portal';
import { Calculator } from '../../pages/calculator/calculator';

@Injectable({ providedIn: 'root' })
export class CalculatorOverlayService {
  private overlayRef: OverlayRef | null = null;

  constructor(private readonly overlay: Overlay, private readonly injector: Injector) {}

  open() {
    if (this.overlayRef) {
      return;
    }

    this.overlayRef = this.overlay.create({
      hasBackdrop: false,
      positionStrategy: this.overlay.position()
        .global()
        .top('100px')
        .right('30px'),
      scrollStrategy: this.overlay.scrollStrategies.reposition()
    });

    const portal = new ComponentPortal(Calculator, null, this.injector);
    this.overlayRef.attach(portal);
  }

  close() {
    if (this.overlayRef) {
      this.overlayRef.detach();
      this.overlayRef = null;
    }
  }

  toggle() {
    this.overlayRef ? this.close() : this.open();
  }
}
