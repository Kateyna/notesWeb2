import { Component, Inject } from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
} from '@angular/material/dialog';
import {MatButtonModule} from '@angular/material/button';


@Component({
  imports: [
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    MatButtonModule
  ],
  selector: 'app-dialog_note_delete',
  styleUrl: './dialog_note_delete.css',
  templateUrl: './dialog_note_delete.html'

})
export class DialologyComponent {
  constructor(
    public dialogRef: MatDialogRef<DialologyComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { name: string }
  ) {}
}
