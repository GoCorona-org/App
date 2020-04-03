package simplifii.framework.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUriExposedException;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.camera.DefaultCameraModule;
import com.esafirm.imagepicker.model.Image;

import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import simplifii.framework.R;
import simplifii.framework.utility.AppConstants;

public class MediaFragment extends Fragment {
    public static final int REQUEST_CODE_GALLARY = 101;
    public static final int REQUEST_CODE_CAMERA = 102;
    public static final int REQUEST_CODE_AUDIO = 103;
    public static final int REQUEST_CODE_PICK_VIDEO = 104;
    private static final int REQUEST_CODE_PICK_GIF = 105;
    public Uri imageUri;
    MediaListener mediaListener;
    String fileName = "JugoApp";
    private DefaultCameraModule cameraModule;

    public String getFileName() {
        return fileName;
    }

    public void getPicture(final MediaListener mediaListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, new String[]{"Camera", "Gallery"});
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    getImageFromCamera(mediaListener, getString(R.string.image_folder_name));
                } else if (which == 1) {
                    getImageFromGallery(mediaListener);
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setTitle(getString(R.string.choose_picture));
        dialog.show();
    }

    public void getImageFromCamera(MediaListener mediaListener, String folderName) throws FileUriExposedException {
        this.mediaListener = mediaListener;
        ImagePicker.create(this).single().folderMode(true).start(REQUEST_CODE_GALLARY);
    }

    public void getImageFromGallery(MediaListener mediaListener) {
        this.mediaListener = mediaListener;
        ImagePicker.create(this).single().folderMode(true).start(REQUEST_CODE_GALLARY);
    }

    public Uri getOutputMediaFileUri(String folderName) throws SecurityException {
        File outputMediaFile = getOutputMediaFile(folderName);
        return getUriFromFile(outputMediaFile);
    }

    private Uri getUriFromFile(File file){
        if (Build.VERSION.SDK_INT >= 24) { //use this if Lollipop_Mr1 (API 22) or above
            return FileProvider.getUriForFile(getActivity(), getActivity().getPackageName()+".fileprovider", file);
        } else {
            return Uri.fromFile(file);
        }
    }

    private File getOutputMediaFile(String folderName) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), folderName);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".png");
        return mediaFile;
    }


    public void getAudioFromPlayer(MediaListener mediaListener) {
        this.mediaListener = mediaListener;
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select Audio "), REQUEST_CODE_AUDIO);
    }

    public void getVideoFromGallary(MediaListener mediaListener) {
        this.mediaListener = mediaListener;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_CODE_PICK_VIDEO);
    }
    public void getGifFromGallary(MediaListener mediaListener) {
        this.mediaListener = mediaListener;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/gif");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Gif"), REQUEST_CODE_PICK_GIF);
    }

    public void getVideoFromCamera(MediaListener mediaListener) {
        this.mediaListener = mediaListener;
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_CODE_PICK_VIDEO);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != -1) {
            return;
        }
        int fileType = 0;

        Log.i("msg", "onActivity result is called... requestCode=" + requestCode);
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:{
                fileType = 1;
                if (imageUri !=null) {
                    mediaListener.setUri(imageUri, AppConstants.MEDIA_TYPES.IMAGE, imageUri.getPath(), fileType);
                }

                break;}
            case REQUEST_CODE_GALLARY:{
                if(data != null){
                    ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
                    if(images != null && images.size()>0){
                        Image image = images.get(0);
                        imageUri = Uri.fromFile(new File(image.getPath()));
                        if (imageUri !=null) {
                            mediaListener.setUri(imageUri, AppConstants.MEDIA_TYPES.IMAGE, image.getPath(), fileType);
                        }
                    }
                    return;
                }else{
                    return;
                }



//                String imagePath = null;
//                try {
//                    imagePath = getFilePath(getActivity(), data.getData());
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
//                fileType = 1;
//                if (data.getData() !=null) {
//                    mediaListener.setUri(data.getData(), AppConstants.MEDIA_TYPES.IMAGE, imagePath, fileType);
//                }
                // mediaListener.setBitmap(data.get,mediaSelectedFrom);
//                mediaListener.setPath(imagePath);
//                break;
            }
            case REQUEST_CODE_AUDIO:{
                if(data!=null){
                    String imagePath = null;
                    try {
                        imagePath = getFilePath(getActivity(), data.getData());
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    Uri uri = data.getData();
                    fileType = 2;
                    if (data.getData() !=null) {
                        mediaListener.setUri(uri, AppConstants.MEDIA_TYPES.AUDIO, imagePath, fileType);
                    }
                }

                break;}
            case REQUEST_CODE_PICK_VIDEO:{
                String imagePath = null;
                try {
                    imagePath = getFilePath(getActivity(), data.getData());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                fileType = 3;
                if (data.getData() !=null) {
                    mediaListener.setUri(data.getData(), AppConstants.MEDIA_TYPES.VIDEO, imagePath, fileType);
                }
                break;}

            case REQUEST_CODE_PICK_GIF:{
                String imagePath = null;
                try {
                    imagePath = getFilePath(getActivity(),data.getData());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                fileType = 4;
                if (data.getData() !=null){
                    mediaListener.setUri(data.getData(), AppConstants.MEDIA_TYPES.GIF, imagePath, fileType);
                }
                break;}
        }

    }
    @SuppressLint("NewApi")
    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String filePath = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            filePath= uri.getPath();
            return filePath;
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getFilePath1(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }


    public static String getUriRealPath(Context ctx, Uri uri)
    {
        String ret = "";

        if( isAboveKitKat() )
        {
            // Android OS above sdk version 19.
            ret = getUriRealPathAboveKitkat(ctx, uri);
        }else
        {
            // Android OS below sdk version 19
            ret = getImageRealPath(ctx.getContentResolver(), uri, null);
        }

        return ret;
    }

    private static String getUriRealPathAboveKitkat(Context ctx, Uri uri)
    {
        String ret = "";

        if(ctx != null && uri != null) {

            if(isContentUri(uri))
            {
                if(isGooglePhotoDoc(uri.getAuthority()))
                {
                    ret = uri.getLastPathSegment();
                }else {
                    ret = getImageRealPath(ctx.getContentResolver(), uri, null);
                }
            }else if(isFileUri(uri)) {
                ret = uri.getPath();
            }else if(isDocumentUri(ctx, uri)){

                // Get uri related document id.
                String documentId = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    documentId = DocumentsContract.getDocumentId(uri);
                }

                // Get uri authority.
                String uriAuthority = uri.getAuthority();

                if(isMediaDoc(uriAuthority))
                {
                    String idArr[] = documentId.split(":");
                    if(idArr.length == 2)
                    {
                        // First item is document type.
                        String docType = idArr[0];

                        // Second item is document real id.
                        String realDocId = idArr[1];

                        // Get content uri by document type.
                        Uri mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        if("image".equals(docType))
                        {
                            mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        }else if("video".equals(docType))
                        {
                            mediaContentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        }else if("audio".equals(docType))
                        {
                            mediaContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        }

                        // Get where clause with real document id.
                        String whereClause = MediaStore.Images.Media._ID + " = " + realDocId;

                        ret = getImageRealPath(ctx.getContentResolver(), mediaContentUri, whereClause);
                    }

                }else if(isDownloadDoc(uriAuthority))
                {
                    // Build download uri.
                    Uri downloadUri = Uri.parse("content://downloads/public_downloads");

                    // Append download document id at uri end.
                    Uri downloadUriAppendId = ContentUris.withAppendedId(downloadUri, Long.valueOf(documentId));

                    ret = getImageRealPath(ctx.getContentResolver(), downloadUriAppendId, null);

                }else if(isExternalStoreDoc(uriAuthority))
                {
                    String idArr[] = documentId.split(":");
                    if(idArr.length == 2)
                    {
                        String type = idArr[0];
                        String realDocId = idArr[1];

                        if("primary".equalsIgnoreCase(type))
                        {
                            ret = Environment.getExternalStorageDirectory() + "/" + realDocId;
                        }
                    }
                }
            }
        }

        return ret;
    }

    /* Check whether current android os version is bigger than kitkat or not. */
    private static  boolean isAboveKitKat()
    {
        boolean ret = false;
        ret = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        return ret;
    }

    /* Check whether this uri represent a document or not. */
    private static boolean isDocumentUri(Context ctx, Uri uri)
    {
        boolean ret = false;
        if(ctx != null && uri != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ret = DocumentsContract.isDocumentUri(ctx, uri);
            }
        }
        return ret;
    }

    /* Check whether this uri is a content uri or not.
     *  content uri like content://media/external/images/media/1302716
     *  */
    private static boolean isContentUri(Uri uri)
    {
        boolean ret = false;
        if(uri != null) {
            String uriSchema = uri.getScheme();
            if("content".equalsIgnoreCase(uriSchema))
            {
                ret = true;
            }
        }
        return ret;
    }

    /* Check whether this uri is a file uri or not.
     *  file uri like file:///storage/41B7-12F1/DCIM/Camera/IMG_20180211_095139.jpg
     * */
    private static boolean isFileUri(Uri uri)
    {
        boolean ret = false;
        if(uri != null) {
            String uriSchema = uri.getScheme();
            if("file".equalsIgnoreCase(uriSchema))
            {
                ret = true;
            }
        }
        return ret;
    }


    /* Check whether this document is provided by ExternalStorageProvider. */
    private static boolean isExternalStoreDoc(String uriAuthority)
    {
        boolean ret = false;

        if("com.android.externalstorage.documents".equals(uriAuthority))
        {
            ret = true;
        }

        return ret;
    }

    /* Check whether this document is provided by DownloadsProvider. */
    private static boolean isDownloadDoc(String uriAuthority)
    {
        boolean ret = false;

        if("com.android.providers.downloads.documents".equals(uriAuthority))
        {
            ret = true;
        }

        return ret;
    }

    /* Check whether this document is provided by MediaProvider. */
    private static boolean isMediaDoc(String uriAuthority)
    {
        boolean ret = false;

        if("com.android.providers.media.documents".equals(uriAuthority))
        {
            ret = true;
        }

        return ret;
    }

    /* Check whether this document is provided by google photos. */
    private static boolean isGooglePhotoDoc(String uriAuthority)
    {
        boolean ret = false;

        if("com.google.android.apps.photos.content".equals(uriAuthority))
        {
            ret = true;
        }

        return ret;
    }

    /* Return uri represented document file real local path.*/
    private static String getImageRealPath(ContentResolver contentResolver, Uri uri, String whereClause)
    {
        String ret = "";

        // Query the uri with condition.
        Cursor cursor = contentResolver.query(uri, null, whereClause, null, null);

        if(cursor!=null)
        {
            String wholeID = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                wholeID = DocumentsContract.getDocumentId(uri);
            }
            String id = wholeID.split(":")[1];
            String[] column = { MediaStore.Video.Media.DATA };
            String sel = MediaStore.Video.Media._ID + "=?";

            cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, column, sel, new String[]{ id }, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                ret = cursor.getString(columnIndex);
            }

            cursor.close();
            return ret;
            /*boolean moveToFirst = cursor.moveToFirst();
            if(moveToFirst)
            {

                // Get columns name by uri type.
                String columnName = "_display_name";

                if( uri==MediaStore.Images.Media.EXTERNAL_CONTENT_URI )
                {
                    columnName = MediaStore.Images.Media.DATA;
                }else if( uri==MediaStore.Audio.Media.EXTERNAL_CONTENT_URI )
                {
                    columnName = MediaStore.Audio.Media.DATA;
                }else if( uri==MediaStore.Video.Media.EXTERNAL_CONTENT_URI )
                {
                    columnName = MediaStore.Video.Media.DATA;
                }

                // Get column index.
                int imageColumnIndex = cursor.getColumnIndex(columnName);

                // Get column value which is the uri related file local path.
                ret = cursor.getString(imageColumnIndex);
            }*/
        }

        return ret;
    }

    public interface MediaListener {

        void setUri(Uri uri, String MediaType, String imagePath, int fileType);
//        void setPath(String imagePath);
    }

}
