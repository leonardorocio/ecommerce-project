import { Injectable } from '@angular/core';
import { Storage, ref, uploadBytesResumable, getDownloadURL} from '@angular/fire/storage';

@Injectable({
  providedIn: 'root'
})
export class FirebaseStorageService {

  constructor(private storage: Storage) { }

  async uploadFile(file: File): Promise<string> {
    if (file) {
      const storageRef = ref(this.storage, file.name);
      const snapshotCompleted = await uploadBytesResumable(storageRef, file);
      return await getDownloadURL(storageRef);
    }
    return Promise.resolve('');
  }
}
