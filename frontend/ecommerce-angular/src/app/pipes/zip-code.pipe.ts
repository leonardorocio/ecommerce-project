import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'zipCode',
})
export class ZipCodePipe implements PipeTransform {
  transform(value: string, ...args: unknown[]): string {
    if (value) {
      value = value.trim();
      if (value.match(/([0-9]{5}-[0-9]{0,3}?)/g)?.[0]) {
        return value;
      } else if (value.length >= 5) {
        return value.substring(0,5) + '-' + value.substring(5);
      }
    }
    return '';
  }
}
