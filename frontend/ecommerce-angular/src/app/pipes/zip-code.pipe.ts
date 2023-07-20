import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'zipCode',
})
export class ZipCodePipe implements PipeTransform {
  transform(value: string, ...args: unknown[]): string {
    if (value.trim().length === 8 && value.indexOf('-') === -1) {
      const newValue = `${value.substring(0,5)}-${value.substring(5)}`;
      return newValue;
    } else if (value.trim().length > 8){
      console.log(value.slice(0,9))
      return value.slice(0,9);
    } else {
      return value;
    }
  }
}
