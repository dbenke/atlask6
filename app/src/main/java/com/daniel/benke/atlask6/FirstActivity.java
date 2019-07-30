package com.daniel.benke.atlask6;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends AppCompatActivity {


    private LinearLayoutManager lLayout;
    public static String nomelogin = "tutu";


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        final List<ItemObject> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(FirstActivity.this);

        final RecyclerView rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setLayoutManager(lLayout);

        final RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(FirstActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);

        rView.addOnItemTouchListener(new RecyclerItemClickListener(this, rView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                Intent nextScreen = new Intent(getApplicationContext(), MastoActivity.class);
                switch (rowListItem.get(position).getName()) {




                    case "Mastócito":
                        //Toast.makeText(view.getContext(), "clicou na lingua]" , Toast.LENGTH_SHORT).show();
                        nextScreen = new Intent(getApplicationContext(), MastoActivity.class);
                        startActivity(nextScreen);

                        break;

                    case "Pólipo Nasal":
                        //Toast.makeText(view.getContext(), "clicou na lingua]" , Toast.LENGTH_SHORT).show();
                        nextScreen = new Intent(getApplicationContext(), PolipoActivity.class);
                        startActivity(nextScreen);

                        break;

                    case "Fígado":
                        //Toast.makeText(view.getContext(), "clicou na lingua]" , Toast.LENGTH_SHORT).show();
                        nextScreen = new Intent(getApplicationContext(), FigadoActivity.class);
                        startActivity(nextScreen);

                        break;

                    case "Cartilagem Hialina":
                        //Toast.makeText(view.getContext(), "clicou na lingua]" , Toast.LENGTH_SHORT).show();
                        nextScreen = new Intent(getApplicationContext(), HialinaActivity.class);
                        startActivity(nextScreen);

                        break;

                    case "Cartilagem Elástica":
                        //Toast.makeText(view.getContext(), "clicou na lingua]" , Toast.LENGTH_SHORT).show();
                        nextScreen = new Intent(getApplicationContext(), ElasticaActivity.class);
                        startActivity(nextScreen);

                        break;
                    case "Osso por Desgaste":
                        //Toast.makeText(view.getContext(), "clicou na lingua]" , Toast.LENGTH_SHORT).show();
                        nextScreen = new Intent(getApplicationContext(), OssoActivity.class);
                        startActivity(nextScreen);

                        break;

                    case "Osso por Descalcificação":
                        //Toast.makeText(view.getContext(), "clicou na lingua]" , Toast.LENGTH_SHORT).show();
                        nextScreen = new Intent(getApplicationContext(), OssocaActivity.class);
                        startActivity(nextScreen);

                        break;
                }

                new Thread(new Runnable() {
                    public void run() {

                        //  uploadFileFTP();


                        uploadFilePHP();

                    }
                }).start();


            }

            @Override
            public void onItemLongClick(View view, int position) {
                //handle longClick if any
            }
        }));


        String MyPREFERENCES = "com.daniel.benke.atlask6";
        SharedPreferences sharedpreferences;


        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        boolean variable = sharedpreferences.getBoolean("NameKey", false);

       /// Toast.makeText(FirstActivity.this, "NameKey " + variable, Toast.LENGTH_SHORT).show();

        if (variable == false) {
            Intent nextScreen = new Intent(getApplicationContext(), MastoActivity.class);
            nextScreen = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(nextScreen);
            editor.putBoolean("NameKey", true);
            editor.apply();
        } else {
            nomelogin = sharedpreferences.getString("loginame", "sharedfalse");
          //  Toast.makeText(FirstActivity.this, "nomelogin " + nomelogin, Toast.LENGTH_SHORT).show();


        }

        //   editor.putString("loginame", nomelogin);
        //   editor.commit();


    }


    public int uploadFilePHP() {


        String filename = "Telas.txt";
        String filepath = "MyFileStorage";
        String upLoadServerUri = "http://aztrau9.000webhostapp.com/uploadconjuntivo.php";
        //String upLoadServerUri = "http://www.benke.ufpr.br/upload.php";

        String sourceFileUri = getExternalFilesDir(filepath) + filename;
        Log.v("sourceFileUri", " exist : " + sourceFileUri);


        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(getExternalFilesDir(filepath), filename);
        //new File(sourceFileUri);

        if (!sourceFile.isFile()) {


            Log.e("uploadFile", "Source File not exist :"
                    + filepath + "" + filename);


            return 0;

        } else {
            int serverResponseCode = 0;
            try {


                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                //conn.setRequestProperty("uploaded_file", fileName);
                conn.setRequestProperty("fileToUpload", sourceFileUri);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"fileToUpload\";filename=\""
                        //                          + sourceFileUri + "\"" + lineEnd);
                        + nomelogin + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                // serverResponseCode = conn.getResponseCode();
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {

                    runOnUiThread(new Runnable() {
                        public void run() {

                            //                          String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                            //                                +" http://www.androidexample.com/media/uploads/"
                            //                              +uploadFileName;

                            //                  messageText.setText(msg);
                           /// Toast.makeText(FirstActivity.this, "File Upload Complete. " + nomelogin,
                           ///         Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {


                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {

                      //  Toast.makeText(FirstActivity.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {


                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                  //      Toast.makeText(FirstActivity.this, "Got Exception : see logcat " + nomelogin, Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload f to srvr Excptn", "Exception : "
                        + e.getMessage(), e);
            }

            return serverResponseCode;

        } // End else block
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_settings:
                // Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT).show();
                Intent nextScreen = new Intent(getApplicationContext(), SobreActivity.class);
                startActivity(nextScreen);
                break;
            default:
                break;
        }

        return true;
    }

    private List<ItemObject> getAllItemList() {

        List<ItemObject> allItems = new ArrayList<ItemObject>();



        allItems.add(new ItemObject("Mastócito", R.drawable.masto01));
        allItems.add(new ItemObject("Pólipo Nasal", R.drawable.polipo01));
        allItems.add(new ItemObject("Fígado", R.drawable.figado01));
        allItems.add(new ItemObject("Cartilagem Hialina", R.drawable.hialina01));
        allItems.add(new ItemObject("Cartilagem Elástica", R.drawable.elastica01));
        allItems.add(new ItemObject("Osso por Desgaste", R.drawable.osso01));
        allItems.add(new ItemObject("Osso por Descalcificação", R.drawable.ossoca01));

        return allItems;
    }


    public static void setNomeLogin(String nomeLogin) {
        FirstActivity.nomelogin = nomeLogin;
    }
}
