import { BreakpointObserver } from '@angular/cdk/layout';
import { CommonModule } from '@angular/common';
import {
  Component,
  ElementRef,
  HostListener,
  OnInit,
  Renderer2,
  ViewChild,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import {
  Router,
  RouterLink,
  RouterLinkActive,
  RouterOutlet,
} from '@angular/router';
import { AuthService } from '../../service/auth/auth-service';
import { MENU_ITEMS } from '../../config/menu.config';
import { Calculator } from '../calculator/calculator';
import { CalculatorOverlayService } from '../../service/common/calculator-overlay.service';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    CommonModule,
    FormsModule
  ],
  templateUrl: './layout.html',
  styleUrls: ['./layout.scss'],
})
export class Layout implements OnInit {
  @ViewChild('navToggler', { static: true }) navToggler!: ElementRef;
  @ViewChild('mainWrapper', { static: true }) mainWrapper!: ElementRef;
  @ViewChild('navContainer', { static: true }) navContainer!: ElementRef;
  @ViewChild('bodyWrapper', { static: true }) bodyWrapper!: ElementRef;
  @ViewChild('calculator') calculator!: Calculator;

  passwordsMatch: boolean = true;
  passwordVisible = false;
  loading = false;
  error = false;
  logoutWarning = false;
  warningCounter: number = 10;
  private readonly inactivityTime: any;
  private bodyWrapperClickListener: any;
  activeMenu: string | null = null;
  activeSubMenu: string | null = null;
  isMobile = false;
  currentTime: string = '';
  private intervalId: any;
  isFullScreen = false;

  menu: any[] = [];
  roles: string[] = [];

  constructor(
    private readonly authService: AuthService,
    private readonly router: Router,
    private readonly renderer: Renderer2,
    private readonly observer: BreakpointObserver,
    private calculatorOverlay: CalculatorOverlayService
  ) { }

  ngOnInit() {
    // Ensure roles are available before filtering
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['/login']);
      return;
    }

    this.router.events.subscribe(() => {
      this.setActiveSubMenu();
    });
    this.setActiveSubMenu();

    this.roles = this.authService.userRoles || [];
    this.menu = this.filterMenuByRoles(MENU_ITEMS);

    // Setup time update
    this.updateTime();
    this.intervalId = setInterval(() => this.updateTime(), 1000);

    // Mobile screen observer
    this.observer
      .observe(['(max-width: 991px)'])
      .subscribe((screenSize: { matches: boolean }) => {
        this.isMobile = screenSize.matches;
      });
  }

  setActiveSubMenu() {
    const url = this.router.url;
    for (let item of this.menu) {
      if (item.isGroup && item.children?.some((sub: any) => url.startsWith(sub.route))) {
        this.activeSubMenu = item.label;
        return;
      }
    }
    this.activeSubMenu = null;
  }

  toggleSubMenu(event: Event, label: string) {
    event.preventDefault();
    this.activeSubMenu = (this.activeSubMenu === label) ? null : label;
  }

  // For main items and sub-items: check if current URL matches
  isActive(item: any): boolean {
    if (item.isGroup && item.children) {
      return item.children.some((sub: any) => this.router.url.startsWith(sub.route));
    }
    return this.router.url.startsWith(item.route);
  }

  // For submenus: open if it is the current group or one of its children is active
  isSubMenuOpen(item: any): boolean {
    if (this.activeSubMenu === item.label) return true;
    return item.children?.some((sub: any) => this.router.url.startsWith(sub.route));
  }

  private filterMenuByRoles(items: any[]): any[] {
    return items
      .filter(item => !item.roles || item.roles.some((role: string) => this.roles.includes(role)))
      .map(item => ({
        ...item,
        children: item.children ? this.filterMenuByRoles(item.children) : null
      }));
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

  getMenuIconClass(menuName: string): string {
    return menuName.toLowerCase().replace(/\s+/g, '-');
  }

  getSubMenuLink(menuName: string, subMenu: string): string {
    const menuSlug = menuName.toLowerCase().replace(/\s+/g, '-');
    const subMenuSlug = subMenu.toLowerCase().replace(/\s+/g, '-');
    return `/${menuSlug}/${subMenuSlug}`;
  }

  ngAfterViewInit() {
    const navToggler = this.navToggler.nativeElement;
    const mainWrapper = this.mainWrapper.nativeElement;
    const navContainer = this.navContainer.nativeElement;
    const bodyWrapper = this.bodyWrapper.nativeElement;

    this.renderer.listen(navToggler, 'click', () => {
      this.renderer.listen(
        navContainer,
        'mouseenter',
        this.hoverMenuOpen.bind(this)
      );
      this.renderer.listen(
        navContainer,
        'mouseleave',
        this.menuClose.bind(this)
      );
      this.renderer.removeClass(mainWrapper, 'hover-menu-open');

      if (mainWrapper.classList.contains('menu-close')) {
        this.menuOpen();
        this.renderer.listen(navContainer, 'mouseenter', () => {
          this.renderer.removeClass(mainWrapper, 'hover-menu-open');
        });
        this.renderer.listen(
          navContainer,
          'mouseleave',
          this.hoverMenuClose.bind(this)
        );
      } else {
        this.menuClose();
        this.renderer.listen(
          navContainer,
          'mouseenter',
          this.hoverMenuOpen.bind(this)
        );
        this.renderer.listen(
          navContainer,
          'mouseleave',
          this.menuClose.bind(this)
        );
      }
    });

    if (this.isMobile) {
      this.menuClose();
      this.bodyWrapperClickListener = this.renderer.listen(
        bodyWrapper,
        'click',
        this.menuClose.bind(this)
      );
    }
  }

  respMenuClose() {
    if (this.isMobile) {
      this.menuClose();
    }
  }

  menuOpen() {
    const mainWrapper = this.mainWrapper.nativeElement;
    this.renderer.removeClass(mainWrapper, 'menu-close');
  }

  menuClose() {
    const mainWrapper = this.mainWrapper.nativeElement;
    this.renderer.addClass(mainWrapper, 'menu-close');
    this.renderer.removeClass(mainWrapper, 'hover-menu-open');
  }

  hoverMenuOpen() {
    if (!this.isMobile) {
      const mainWrapper = this.mainWrapper.nativeElement;
      this.renderer.removeClass(mainWrapper, 'menu-close');
      this.renderer.addClass(mainWrapper, 'hover-menu-open');
    }
  }

  hoverMenuClose() {
    if (!this.isMobile) {
      const mainWrapper = this.mainWrapper.nativeElement;
      this.renderer.removeClass(mainWrapper, 'menu-close');
      this.renderer.removeClass(mainWrapper, 'hover-menu-open');
    }
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: Event) {
    const mainWrapper = this.mainWrapper.nativeElement;
    const bodyWrapper = this.bodyWrapper.nativeElement;

    const menuClose = () => {
      this.renderer.addClass(mainWrapper, 'menu-close');
    };

    if (this.isMobile) {
      this.renderer.addClass(mainWrapper, 'menu-close');
      this.bodyWrapperClickListener ??= this.renderer.listen(
        bodyWrapper,
        'click',
        menuClose
      );
      return; // exit early
    }

    if (this.bodyWrapperClickListener) {
      this.renderer.removeClass(mainWrapper, 'menu-close');
      this.bodyWrapperClickListener();
      this.bodyWrapperClickListener = null;
    }
  }

  @HostListener('document:mousemove')
  @HostListener('document:keydown')
  @HostListener('document:click')
  @HostListener('document:touchstart')
  resetInactivityTimer() {
    if (this.inactivityTime) {
      clearTimeout(this.inactivityTime);
    }
    // Optional: reset and start timeout logic here
  }
  logout() {
    this.authService.logout();
    // this.toast.showSuccess('Logout Successful.');
    this.router.navigate(['/login']);
  }
}
