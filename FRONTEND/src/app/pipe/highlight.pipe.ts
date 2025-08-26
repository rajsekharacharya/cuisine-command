
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'highlight'
})
export class HighlightPipe implements PipeTransform {
  transform(value: string, searchTerm: string | string[]): string {
    if (!value || !searchTerm) {
      return value;
    }

    // Ensure searchTerm is an array
    const terms = Array.isArray(searchTerm) ? searchTerm : searchTerm.split(';');
    const escapedTerms = terms
      .filter(term => term) // Ignore empty terms
      .map(term => term.trim().replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&')); // Escape special characters

    if (escapedTerms.length === 0) {
      return value; // Return unmodified if no terms to search
    }
    // const regex = new RegExp(`(${search})`, 'gi'); // 'gi' ensures case-insensitive matching

    const regex = new RegExp(`(${escapedTerms.join('|')})`, 'gi'); // Create regex for all terms

    // Replace matched text with wrapped span
    return value.replace(regex, '<span class="highlight">$1</span>');
  }
}
