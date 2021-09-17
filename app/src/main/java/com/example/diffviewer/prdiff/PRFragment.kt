package com.example.diffviewer.prdiff

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.diffviewer.R
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import android.widget.Spinner
import androidx.annotation.RequiresApi
import com.example.diffviewer.ViewModelFactory
import com.example.diffviewer.prs.RepoPRFragment
import com.example.diffviewer.retrofit.model.PullRequestResponse
import com.example.diffviewer.retrofit.model.Status
import com.example.diffviewer.retrofit.rest.ApiClient
import com.example.diffviewer.retrofit.rest.ApiHelper
import com.google.android.material.snackbar.Snackbar
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import java.io.File
import java.io.BufferedReader
import java.io.InputStream
import java.lang.StringBuilder
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit


class PRFragment  : Fragment() {

    private lateinit var mView: View
    private lateinit var diffWebtView: WebView
    private lateinit var spinner: Spinner
    private lateinit var viewModel: PRViewModel

    private var username = ""
    private var repoName = ""
    private var pullnumber = 0
    private var allSeperateDiffs = mutableListOf<PRDiffLines>()

    private var disposable = Disposables.disposed()

    private val DIFF_FILE_NAME = "process.diff"

    private val fileDownloader by lazy {
        FileDownloader(
            OkHttpClient.Builder().build()
        )
    }

    companion object {
        val TAG: String = PRFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            username = it.getString("username", "")
            repoName = it.getString("repoName", "")
            pullnumber = it.getInt("pullnumber", 0)
        }
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(ApiClient.apiService))
        ).get(PRViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mView = inflater.inflate(R.layout.fragment_pull_request, container, false)
        diffWebtView = mView.findViewById(R.id.diffWebView)
        diffWebtView.settings.setJavaScriptEnabled(true)

        spinner = mView.findViewById(R.id.pBar) as Spinner
        spinner.setVisibility(View.VISIBLE)

        setupObservers()

        return mView;
    }

    fun Fragment.isNetworkAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    private fun setupObservers() {
        if (activity?.baseContext?.let { isNetworkAvailable() }!!) {
            viewModel.getPullRequest(username, repoName, pullnumber).observe(viewLifecycleOwner, {
                it?.let {  resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { pr -> retrieve(pr) }
                        }
                        Status.ERROR -> {
                            Snackbar.make(
                                activity?.window?.decorView?.rootView!!,
                                R.string.error,
                                Snackbar.LENGTH_LONG
                            ).show()
                            Log.d(RepoPRFragment.TAG, it.message.toString())
                        }
                        Status.LOADING -> {
                        }
                    }
                }
            })
        } else {
            // network is not present then show message
            Snackbar.make(
                activity?.window?.decorView?.rootView!!,
                R.string.network_error,
                Snackbar.LENGTH_LONG
            ).setAction("Retry") {
                setupObservers()
            }.show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun retrieve(pr: PullRequestResponse) {

        val targetFile = File(context?.cacheDir, DIFF_FILE_NAME)

        disposable = fileDownloader.download(pr.diff_url, targetFile)
            .throttleFirst(2, TimeUnit.SECONDS)
            .toFlowable(BackpressureStrategy.LATEST)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Toast.makeText(activity, "$it% Downloaded", Toast.LENGTH_SHORT).show()
            }, {
                Toast.makeText(activity, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }, {
                Toast.makeText(activity, "Completed Download", Toast.LENGTH_SHORT).show()

                // Debugging purpose to verify diff file downloaded
                /*
                val bufferedReader: BufferedReader = targetFile.bufferedReader()
                val inputString = bufferedReader.use { it.readText()}
                val sb = StringBuilder("<html><body>")
                sb.append(inputString)
                sb.append("</body></html>")
                diffWebtView.loadData(sb.toString(), "text/html", "UTF-8")
                */

                /*
                Here is a sample diff format. A new diff starts on every 'diff --git'.
                I added example of file diff, ername file and delete file.

diff --git a/MagicalRecord.xcodeproj/project.pbxproj b/MagicalRecord.xcodeproj/project.pbxproj
index 5f61ee43..4fdbd9b6 100644
--- a/MagicalRecord.xcodeproj/project.pbxproj
+++ b/MagicalRecord.xcodeproj/project.pbxproj
@@ -1785,6 +1785,7 @@
 				DSTROOT = "/tmp/$(PROJECT_NAME)$(EFFECTIVE_PLATFORM_NAME).dst";
 				DYLIB_COMPATIBILITY_VERSION = 3.0;
 				DYLIB_CURRENT_VERSION = 3.0.0;
+				ENABLE_NS_ASSERTIONS = NO;
 				ENABLE_STRICT_OBJC_MSGSEND = YES;
 				GCC_GENERATE_TEST_COVERAGE_FILES = YES;
 				GCC_PREPROCESSOR_DEFINITIONS = NDEBUG;
diff --git a/Docs/Installation.md b/Docs/Installation.md
deleted file mode 100644
diff --git a/Docs/DefaultManagedObjectContext.md b/Documentation/DefaultManagedObjectContext.md
similarity index 100%
rename from Docs/DefaultManagedObjectContext.md
rename to Documentation/DefaultManagedObjectContext.md
...
...
...
diff --git a/Library/Categories/CoreData/Import/NSAttributeDescription+MagicalDataImport.m b/Library/Categories/CoreData/Import/NSAttributeDescription+MagicalDataImport.m
index 0dbe9496..9a881338 100644
--- a/Library/Categories/CoreData/Import/NSAttributeDescription+MagicalDataImport.m
+++ b/Library/Categories/CoreData/Import/NSAttributeDescription+MagicalDataImport.m
@@ -116,7 +116,7 @@ - (BOOL)MR_isColorAttributeType
 - (id)MR_valueForKeyPath:(NSString *)keyPath fromObjectData:(id)objectData
 {
     id value = [objectData valueForKeyPath:keyPath];
-    if ([value isEqual:[NSNull null]])
+    if (value == [NSNull null])
     {
         value = nil;
     }

*/
                // Clean up the '.diff' file for display
                val inputStream2: InputStream = targetFile.inputStream()
                val reader = inputStream2.bufferedReader(Charset.defaultCharset())
                var diffLines = PRDiffLines();//mutableListOf<String>()
                var lines : MutableList<String> = mutableListOf<String>()    // diff individual lines
                for (line in reader.lines()) {
                    println(line)
                    if (line.startsWith("diff --git ") == true) {
                        if (allSeperateDiffs.count() > 0) {
                            allSeperateDiffs.add(diffLines)
                        }
                        diffLines = PRDiffLines();
                        diffLines.lines.add(line)
                    }
                    else {
                        diffLines.lines.add(line)
                    }
                }
                allSeperateDiffs.add(diffLines)
                reader.close()

                targetFile.delete();
                spinner.setVisibility(View.GONE)
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

}