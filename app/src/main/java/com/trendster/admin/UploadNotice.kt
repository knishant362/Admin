package com.trendster.admin

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.card.MaterialCardView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.net.URI

class UploadNotice : AppCompatActivity() {

    lateinit var addNotice: MaterialCardView
    var REQ = 1
    lateinit var bitmap : Bitmap
    lateinit var imgNoticePreview : ImageView
    lateinit var etNoticeTitle : EditText
    lateinit var btnUploadNotice : Button
    lateinit var reference: DatabaseReference
    lateinit var storageReference: StorageReference
    lateinit var downloadUrl : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_notice)

        reference = FirebaseDatabase.getInstance().reference
        storageReference = FirebaseStorage.getInstance().reference

        addNotice = findViewById(R.id.addNotice)
        imgNoticePreview = findViewById(R.id.imgNoticePreview)
        etNoticeTitle = findViewById(R.id.etNoticeTitle)
        btnUploadNotice = findViewById(R.id.btnUploadNotice)

        addNotice.setOnClickListener { openGallery() }

        btnUploadNotice.setOnClickListener {
            if(etNoticeTitle.text.toString().isEmpty()){
                etNoticeTitle.error = "Empty"
                etNoticeTitle.requestFocus()
            }else if(bitmap == null){
                uploadData()
            }else{
                uploadImage()
            }

        }
    }

    private fun uploadImage() {
        var baos : ByteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG , 50 , baos)
        var finalImg = baos.toByteArray()

        var filePath : StorageReference
        filePath = storageReference.child("Notice").child(finalImg.toString()+"jpg")

        var uploadTask = filePath.putBytes(finalImg)
        uploadTask.addOnCompleteListener(this@UploadNotice , object : OnCompleteListener<UploadTask.TaskSnapshot>{


            override fun onComplete(p0: Task<UploadTask.TaskSnapshot> , task : UploadTask) {

                if(task.isSuccessful){
                    uploadTask.addOnSuccessListener {
                        OnSuccessListener<UploadTask.TaskSnapshot> {
                            filePath.downloadUrl.addOnSuccessListener (object :
                                OnSuccessListener<Uri>{
                                override fun onSuccess(uri: Uri?) {
                                    downloadUrl = uri.toString()
                                    uploadData()

                                }

                            }
                            ){
                            }
                        }


                    }
                }else{

                    Toast.makeText(this@UploadNotice, "Error Image not upload" , Toast.LENGTH_SHORT).show()
                }
            }

            override fun onComplete(p0: Task<UploadTask.TaskSnapshot>) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun uploadData() {
        TODO("Not yet implemented")
    }

    private fun openGallery() {
        val pickImage : Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickImage , REQ)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ && resultCode == RESULT_OK) {

            val uri : Uri? = data?.data
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)

            imgNoticePreview.setImageBitmap(bitmap)

        }
    }
}
