
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Chart, ChartConfiguration, ChartData, registerables } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { ApiResponse } from '../../../interfaces/api-response';
import { DashboardData } from '../../../interfaces/dashboard-interface';
import { DashboardService } from '../../../service/service/dashboard-service';

// Register all Chart.js components
Chart.register(...registerables);

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, BaseChartDirective],
  templateUrl: './admin-dashboard.html',
  styleUrls: ['./admin-dashboard.scss'],
})
export class AdminDashboard implements OnInit {
  dashboardData: DashboardData | null = null;
  loading = true;
  error: string | null = null;
  startDate: string | undefined;
  endDate: string | undefined;

  // Chart configurations
  revenueChartData: ChartData<'line'> = { labels: [], datasets: [] };
  orderStatusChartData: ChartData<'pie'> = { labels: [], datasets: [] };
  topDishesChartData: ChartData<'bar'> = { labels: [], datasets: [] };
  orderTypeChartData: ChartData<'doughnut'> = { labels: [], datasets: [] };
  peakHoursChartData: ChartData<'bar'> = { labels: [], datasets: [] };
  paymentMethodChartData: ChartData<'doughnut'> = { labels: [], datasets: [] };

  chartOptions: ChartConfiguration['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: 'top',
        labels: {
          color: '#2a2e2e',
          font: {
            size: 14,
            family: 'Roboto, sans-serif',
          },
        },
      },
      tooltip: {
        enabled: true,
        backgroundColor: 'rgba(255, 255, 255, 0.95)',
        titleColor: '#2a2e2e',
        bodyColor: '#2a2e2e',
        borderColor: '#8b0000',
        borderWidth: 1,
        cornerRadius: 8,
        padding: 12,
        titleFont: { family: 'Playfair Display, serif', size: 16 },
        bodyFont: { family: 'Roboto, sans-serif', size: 14 },
      },
    },
    scales: {
      x: {
        grid: { color: 'rgba(0, 0, 0, 0.05)' },
        ticks: { color: '#5c4033', font: { family: 'Roboto, sans-serif' } },
      },
      y: {
        grid: { color: 'rgba(0, 0, 0, 0.05)' },
        ticks: { color: '#5c4033', font: { family: 'Roboto, sans-serif' } },
      },
    },
    animation: {
      duration: 1500,
      easing: 'easeOutCubic',
    },
    elements: {
      line: {
        tension: 0.4,
        borderWidth: 4,
      },
      point: {
        radius: 6,
        hoverRadius: 9,
        backgroundColor: '#ffca28',
        borderWidth: 2,
      },
      bar: {
        borderWidth: 2,
        borderRadius: 6,
      },
    },
  };

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    // Initialize with default date range (today in user's local timezone)
    const today = new Date();
    this.startDate = this.formatDate(today);
    this.endDate = this.formatDate(today);
    this.fetchDashboardData();
  }

  // Format date to yyyy-MM-dd in local timezone
  private formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  fetchDashboardData(): void {
    this.loading = true;
    const params = {
      startDate: this.startDate,
      endDate: this.endDate,
    };
    this.dashboardService.getDashboardData(params).subscribe({
      next: (response: ApiResponse<DashboardData>) => {
        if (response.status) {
          console.log('Dashboard Data:', response.data);
          this.dashboardData = response.data ?? null;
          this.setupCharts();
          this.loading = false;
        } else {
          this.error = response.message || 'Failed to fetch dashboard data';
          this.loading = false;
        }
      },
      error: (err) => {
        this.error = err.message || 'An error occurred while fetching data';
        this.loading = false;
      },
    });
  }

  onDateRangeChange(): void {
    if (this.startDate && this.endDate) {
      this.fetchDashboardData();
    }
  }

  private setupCharts(): void {
    if (!this.dashboardData) return;

    // Helper function to create gradients
    const createGradient = (ctx: CanvasRenderingContext2D, color1: string, color2: string) => {
      const gradient = ctx.createLinearGradient(0, 0, 0, 300);
      gradient.addColorStop(0, color1);
      gradient.addColorStop(1, color2);
      return gradient;
    };

    // Revenue Chart
    const revenueCtx = document.createElement('canvas').getContext('2d');
    if (revenueCtx) {
      this.revenueChartData = {
        labels: ['Today', 'Week', 'Month', 'Year'],
        datasets: [
          {
            label: 'Revenue (₹)',
            data: [
              this.dashboardData.revenue.today,
              this.dashboardData.revenue.week,
              this.dashboardData.revenue.month,
              this.dashboardData.revenue.year,
            ],
            borderColor: '#8b0000',
            backgroundColor: createGradient(revenueCtx, 'rgba(139, 0, 0, 0.4)', 'rgba(139, 0, 0, 0)'),
            fill: true,
            pointBackgroundColor: '#ffca28',
            pointBorderColor: '#8b0000',
            pointBorderWidth: 2,
          },
        ],
      };
    }

    // Order Status Pie Chart
    this.orderStatusChartData = {
      labels: ['Completed', 'Canceled'],
      datasets: [
        {
          data: [
            this.dashboardData.completedOrders.today,
            this.dashboardData.canceledOrders.today,
          ],
          backgroundColor: ['#1b5e20', '#b71c1c'],
          hoverBackgroundColor: ['#4caf50', '#ef5350'],
          borderColor: '#fff',
          borderWidth: 2,
        },
      ],
    };

    // Top Dishes Bar Chart
    const topDishesCtx = document.createElement('canvas').getContext('2d');
    if (topDishesCtx) {
      this.topDishesChartData = {
        labels: this.dashboardData.topSellingDishes.map((dish) => dish.name),
        datasets: [
          {
            label: 'Quantity Sold',
            data: this.dashboardData.topSellingDishes.map((dish) => dish.quantitySold),
            backgroundColor: createGradient(topDishesCtx, '#0288d1', '#29b6f6'),
            borderColor: '#01579b',
            borderWidth: 2,
          },
        ],
      };
    }

    // Order Type Doughnut Chart
    this.orderTypeChartData = {
      labels: ['Pickup', 'Delivery', 'DINE_IN'],
      datasets: [
        {
          data: [
            this.dashboardData.orderTypeBreakdown.PICKUP,
            this.dashboardData.orderTypeBreakdown.DELIVERY,
            this.dashboardData.orderTypeBreakdown.DINE_IN,
          ],
          backgroundColor: ['#ef6c00', '#8b0000', '#1b5e20'],
          hoverBackgroundColor: ['#ff8f00', '#c2185b', '#4caf50'],
          borderColor: '#fff',
          borderWidth: 2,
        },
      ],
    };

    // Peak Hours Bar Chart
    const peakHoursCounts = Object.values(this.dashboardData.peakHours);
    const maxCount = Math.max(...peakHoursCounts, 1);
    const peakCtx = document.createElement('canvas').getContext('2d');
    if (peakCtx) {
      this.peakHoursChartData = {
        labels: Object.keys(this.dashboardData.peakHours).map((hour) => `${hour}:00`),
        datasets: [
          {
            label: 'Orders',
            data: peakHoursCounts,
            backgroundColor: peakHoursCounts.map((count) =>
              createGradient(peakCtx, `rgba(139, 0, 0, ${Math.max(0.4, count / maxCount)})`, `rgba(198, 24, 91, ${Math.max(0.2, count / maxCount)})`)
            ),
            borderColor: '#8b0000',
            borderWidth: 2,
          },
        ],
      };
    }

    // Payment Method Doughnut Chart
    this.paymentMethodChartData = {
      labels: ['Cash', 'Card', 'UPI'],
      datasets: [
        {
          data: [
            this.dashboardData.paymentMethodDistribution.CASH,
            this.dashboardData.paymentMethodDistribution.CARD,
            this.dashboardData.paymentMethodDistribution.UPI,
          ],
          backgroundColor: ['#b71c1c', '#01579b', '#1b5e20'],
          hoverBackgroundColor: ['#ef5350', '#29b6f6', '#4caf50'],
          borderColor: '#fff',
          borderWidth: 2,
        },
      ],
    };
  }
}
