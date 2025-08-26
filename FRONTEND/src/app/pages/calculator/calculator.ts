import { Component, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CdkDrag, DragDropModule } from '@angular/cdk/drag-drop';
import { Parser } from 'expr-eval';

@Component({
  selector: 'app-calculator',
  standalone: true,
  imports: [CommonModule, FormsModule, CdkDrag, DragDropModule],
  templateUrl: './calculator.html',
  styleUrls: ['./calculator.scss']
})
export class Calculator {
  input = '';
  visible = true;
  lastKey: string | null = null;

  toggle() {
    this.visible = !this.visible;
  }

  append(value: string) {
    this.input += value;
  }

  backspace() {
    this.input = this.input.slice(0, -1);
  }

  clear() {
    this.input = '';
  }

calculate() {
  try {
    const sanitized = this.input.replace('%', '/100');
    const parser = new Parser();
    const result = parser.evaluate(sanitized);
    this.input = result.toString();
  } catch {
    this.input = 'Error';
  }
}

  @HostListener('window:keydown', ['$event'])
  handleKeyboardInput(event: KeyboardEvent) {
    if (!this.visible) return;

    const key = event.key;
    this.lastKey = key;
    setTimeout(() => (this.lastKey = null), 150);

    if (/[\d\+\-\*\/\.\%\(\)]/.test(key)) {
      this.append(key);
    } else if (key === 'Enter' || key === '=') {
      this.calculate();
      event.preventDefault();
    } else if (key === 'Escape' || key.toUpperCase() === 'C') {
      this.clear();
    } else if (key === 'Backspace') {
      this.backspace();
    }
  }
}
