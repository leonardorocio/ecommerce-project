import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'zipCode'
})
export class ZipCodePipe implements PipeTransform {

  transform(value: string, ...args: unknown[]): string {
    value = value.trim();
    if (value.length == 8) {
      return value.substring(0,5) + '-' + value.substring(5);
    }
    return value;
  }

}
