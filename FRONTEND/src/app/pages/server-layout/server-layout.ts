import { CommonModule } from '@angular/common';
import {
  Component,
  HostListener,
  OnInit,
  ViewChild
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import {
  Router,
  RouterOutlet
} from '@angular/router';
import { AuthService } from '../../service/auth/auth-service';
import { CalculatorOverlayService } from '../../service/common/calculator-overlay.service';
import { Calculator } from '../calculator/calculator';


@Component({
  selector: 'app-server-layout',
  imports: [RouterOutlet,
    CommonModule,
    FormsModule],
  templateUrl: './server-layout.html',
  styleUrl: './server-layout.scss'
})
export class ServerLayout implements OnInit {
  @ViewChild('calculator') calculator!: Calculator;
  loading = false;
  isMobile = false;
  currentTime: string = '';
  private intervalId: any;
  isFullScreen = false;

  constructor(
    private readonly authService: AuthService,
    private readonly router: Router,
    private calculatorOverlay: CalculatorOverlayService
  ) { }

  ngOnInit() {
    // Ensure roles are available before filtering
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['/login']);
      return;
    }

    // Setup time update
    this.updateTime();
    this.intervalId = setInterval(() => this.updateTime(), 1000);
  }

  toggleFullScreen(): void {
    if (!this.isFullScreen) {
      this.enterFullScreen();
    } else {
      this.exitFullScreen();
    }
  }

  @HostListener('window:keydown', ['$event'])
  handleKeyDown(event: KeyboardEvent) {
    if (event.key === 'F11') {
      event.preventDefault(); // prevent default fullscreen behavior
      this.toggleFullScreen();
    }
  }

  enterFullScreen(): void {
    const elem = document.documentElement;
    if (elem.requestFullscreen) {
      elem.requestFullscreen();
    } else if ((elem as any).webkitRequestFullscreen) {
      (elem as any).webkitRequestFullscreen(); // Safari
    } else if ((elem as any).msRequestFullscreen) {
      (elem as any).msRequestFullscreen(); // IE11
    }
    this.isFullScreen = true;
  }

  exitFullScreen(): void {
    if (document.exitFullscreen) {
      document.exitFullscreen();
    } else if ((document as any).webkitExitFullscreen) {
      (document as any).webkitExitFullscreen(); // Safari
    } else if ((document as any).msExitFullscreen) {
      (document as any).msExitFullscreen(); // IE11
    }
    this.isFullScreen = false;
  }

  openCalculator() {
    this.calculatorOverlay.toggle();
  }

  @HostListener('window:keydown.f2', ['$event'])
  onF2(event: Event) {
    const keyboardEvent = event as KeyboardEvent;
    this.calculatorOverlay.toggle();
  }

  @HostListener('document:fullscreenchange')
  @HostListener('document:webkitfullscreenchange')
  @HostListener('document:mozfullscreenchange')
  @HostListener('document:MSFullscreenChange')
  onFullScreenChange(): void {
    const fullscreenElement = document.fullscreenElement ||
      (document as any).webkitFullscreenElement ||
      (document as any).mozFullScreenElement ||
      (document as any).msFullscreenElement;
    this.isFullScreen = !!fullscreenElement;
  }


  updateTime() {
    const now = new Date();
    this.currentTime = now.toLocaleString(); // You can customize format
  }

  ngOnDestroy(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

  logout() {
    this.authService.logout();
    // this.toast.showSuccess('Logout Successful.');
    this.router.navigate(['/login']);
  }


}
