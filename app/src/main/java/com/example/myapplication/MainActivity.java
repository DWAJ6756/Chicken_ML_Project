package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.Interpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {

    Button camera, gallery;
    ImageView imageView;
    TextView result;
    int imageSize = 32;
    int NUM_LABELS = 4; // Number of output classes
    private Interpreter interpreter; // Declare Interpreter as a member variable
    private int inputImageWidth; // Width of the input image
    private int inputImageHeight; // Height of the input image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera = findViewById(R.id.button);
        gallery = findViewById(R.id.button2);

        result = findViewById(R.id.result);
        imageView = findViewById(R.id.imageView);

        // Load the model when the activity is created
        try {
            interpreter = new Interpreter(loadModelFile());

            // Extract input image width and height from the model
            int[] inputShape = interpreter.getInputTensor(0).shape();
            inputImageWidth = inputShape[1];
            inputImageHeight = inputShape[2];
        } catch (IOException e) {
            e.printStackTrace();
        }

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 3);
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(cameraIntent, 1);
            }
        });
    }

    // Method to load the TensorFlow Lite model from the assets folder
    private MappedByteBuffer loadModelFile() throws IOException {
        // Constructing the file path to the model.tflite file inside the ml folder
        File modelFile = new File("app/ml/model.tflite");

        // Check if the file exists
        if (modelFile.exists()) {
            //throw new FileNotFoundException("Model file not found: " + modelFile.getAbsolutePath());
            result.setText("hello loaded");
        }

        // Open FileInputStream for the model file
        FileInputStream inputStream = new FileInputStream(modelFile);

        // Get FileChannel for FileInputStream
        FileChannel fileChannel = inputStream.getChannel();

        // Map the model file to memory
        long startOffset = 0;
        long declaredLength = modelFile.length();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }


    // Method to classify the image using the TensorFlow Lite model
    // Method to classify the image using the TensorFlow Lite model
// Method to classify the image using the TensorFlow Lite model
    // Method to classify the image using the TensorFlow Lite model
    // Method to classify the image using the TensorFlow Lite model
//    public void classifyImage(Bitmap image) {
//        try {
//            // Resize the input image to match the expected input size of the model
//            Bitmap resizedImage = resizeImageToByteSize(image, 602112);
//
//            // Convert the resized image to a TensorImage
//            TensorImage inputImageBuffer = TensorImage.fromBitmap(resizedImage);//tensor flow object
//
//            // Prepare input and output tensors
//            TensorBuffer outputProbabilityBuffer = TensorBuffer.createFixedSize(new int[]{1, NUM_LABELS}, DataType.FLOAT32);
//
//            // Run inference using the loaded TensorFlow Lite model
//            interpreter.run(inputImageBuffer.getBuffer(), outputProbabilityBuffer.getBuffer());
//
//            // Post-processing: find the index with the highest probability
//            float[] probabilities = outputProbabilityBuffer.getFloatArray();
//            int maxIndex = 0;
//            float maxProbability = -1;
//            for (int i = 0; i < NUM_LABELS; i++) {
//                if (probabilities[i] > maxProbability) {
//                    maxProbability = probabilities[i];
//                    maxIndex = i;
//                }
//            }
//
//            // Display the result
//            String[] labels = {"cocci", "healthy", "ncd", "salmo"};
//            String s =labels[maxIndex];
//            if(!s.isEmpty()) {
//                System.out.println("hello");
//            }
//        } catch (Exception e) {
//            // Handle the exception
//            e.printStackTrace();
//        }
//    }



    // Function to resize an image to a specific byte size
    // Function to resize an image to a specific byte size
//    public Bitmap resizeImageToByteSize(Bitmap image, int targetSizeBytes) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        int quality = 100; // Initial quality
//        image.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
//        int initialSize = outputStream.size();
//
//        // Check if the initial size is already smaller than the target size
//        if (initialSize <= targetSizeBytes) {
//            return image; // No need to resize, return the original image
//        }
//
//        // Compress the image until its size is less than or equal to the target size
//        while (outputStream.size() > targetSizeBytes && quality > 0) {
//            outputStream.reset(); // Reset the stream to discard the previous compression attempt
//            quality -= 10; // Reduce the quality
//            image.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
//        }
//
//        byte[] byteArray = outputStream.toByteArray();
//        // Convert the compressed byte array back to Bitmap
//        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//    }






   // @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(resultCode == RESULT_OK){
//            if(requestCode == 3){
//                Bitmap image = (Bitmap) data.getExtras().get("data");
//                int dimension = Math.min(image.getWidth(), image.getHeight());
//                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
//                imageView.setImageBitmap(image);
//
//                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
//                classifyImage(image);
//            } else {
//                Uri dat = data.getData();
//                Bitmap image = null;
//                try {
//                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                imageView.setImageBitmap(image);
//
//                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
//                classifyImage(image);
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}
