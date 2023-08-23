package fr.jnda.android.dexloading.sample

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import dalvik.system.DexClassLoader
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class MainActivity : AppCompatActivity() {
    private lateinit var mainContent: ConstraintLayout
    private val TAG = "MainActivity"
    private val dexFileName = "dexloader.dex"
    private var dexLoader: DexClassLoader? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainContent = findViewById(R.id.main_content)

        findViewById<Button>(R.id.btn_dex).setOnClickListener {
            loadDex()
        }
        findViewById<Button>(R.id.btn_inmemory).setOnClickListener {

        }
        findViewById<Button>(R.id.btn_execute_dex).setOnClickListener {
            executeDex()
        }
        findViewById<Button>(R.id.btn_execute_memory).setOnClickListener {

        }
    }

    private fun showError(message: String){
        Snackbar.make(mainContent,message,Snackbar.LENGTH_SHORT).show()
    }

    private fun loadDex(){
        try {
            val dexFile = File(filesDir, ("payload.dex"))
            copyFromAssetsToCache(dexFile)
            Log.d(TAG, "executeDex: ${dexFile.length()}")

            Log.d(TAG, "executeDex: File existe ? ${dexFile.exists()}")
            val tmpDir = getDir("dex", 0);

            dexLoader = DexClassLoader(
                dexFile.absolutePath,
                tmpDir.absolutePath,
                null,
                this.javaClass.classLoader
            )
            showError("Dex chargé avec succès")
        } catch (e: FileNotFoundException) {
            showError(getString(R.string.dexNotFound))
        }  catch (e: Exception){
            Log.d(TAG, "executeDex: $e ")
            e.message?.let { showError(it) }
        }
    }
    private fun executeDex() {
        dexLoader?.let {dx ->
            try {
                val loadClass =
                    dx.loadClass("fr.jnda.android.dexloading.payload.StringValue")
                val checkMethod = loadClass.getMethod("generateRandomString")
                val cl_in = loadClass.newInstance()
                val text = checkMethod.invoke(cl_in) as String
                Log.d(TAG, "executeDex: $text")
                showError("Valeur random de text $text ( depuis le fichier dex )")
            } catch (e: ClassNotFoundException) {
                showError("La classe recherchée n'existe pas")
            } catch (e: Exception) {
                Log.d(TAG, "executeDex: $e ")
                e.message?.let { showError(it) }
            }
        }?: kotlin.run {
            showError("Le fichier dex n'est pas chargé")
        }
    }


    private fun copyFromAssetsToCache(directory: File) {
        val assetManager = assets
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null
        try {
            inputStream = assetManager.open(dexFileName)
            outputStream = FileOutputStream(directory)
            copyFile(inputStream, outputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
            outputStream?.flush()
            outputStream?.close()
        }
    }

    @Throws(IOException::class)
    private fun copyFile(inputStream: InputStream, outputStream: OutputStream) {
        val buffer = ByteArray(1024)
        var read: Int = inputStream.read(buffer)
        while (read != -1) {
            outputStream.write(buffer, 0, read)
            read = inputStream.read(buffer)
        }
    }

    private fun loadInMemory(){

    }
}