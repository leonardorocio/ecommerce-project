import { Injectable } from '@angular/core';
import Swal, { SweetAlertOptions } from 'sweetalert2';

@Injectable({
  providedIn: 'root',
})
export class AlertService {
  constructor() {}

  async question(title: string, message?: string): Promise<boolean> {
    const result = await Swal.fire({
      title: title,
      message: message ?? '',
      icon: 'question',
      showCancelButton: true,
      confirmButtonColor: '#51A351',
      confirmButtonText: 'Confirmar',
    } as SweetAlertOptions);
    return result.value;
  }

  async warning(title: string, message?: string): Promise<boolean> {
    const result = await Swal.fire({
      title: title,
      message: message ?? '',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#ff0000',
      confirmButtonText: 'Confirmar',
    } as SweetAlertOptions);
    return result.value;
  }

  async info(title: string, message?: string): Promise<boolean> {
    const result = await Swal.fire({
      title: title,
      message: message ?? '',
      icon: 'info',
      confirmButtonColor: '#0D6EFD',
      confirmButtonText: 'OK',
    } as SweetAlertOptions);
    return result.value;
  }
}
