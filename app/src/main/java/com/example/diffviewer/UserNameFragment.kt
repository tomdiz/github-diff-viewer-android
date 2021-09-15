package com.example.diffviewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import com.example.diffviewer.repos.UserReposFragment
import com.example.diffviewer.utils.replaceFragment


class UserNameFragment : Fragment(), View.OnClickListener {

    private lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_username, container, false)
        //onClicks()
        val continueBtn: Button = mView.findViewById(R.id.btnContinue)
        continueBtn.setOnClickListener(this)
        return mView.rootView
    }

    fun String?.isValid(): Boolean {
        return this != null && this.isNotEmpty() && this.isNotBlank()
    }

    companion object {
        private const val TAG = "UserNameFragment"
        fun newInstance(): UserNameFragment {
            return UserNameFragment()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnContinue -> {
                val userNameTextView: TextView = mView.findViewById(R.id.edtUserName)
                val userName = userNameTextView.editableText.toString()
                if (userName.isValid()) {
                    openFragment(UserReposFragment(), userName)
                } else {
                    Toast.makeText(activity,getString(R.string.info_valid_username),Toast.LENGTH_SHORT).show();
                }
            }

            else -> {
            }
        }
    }

    fun Fragment.openFragment(fragment: Fragment, username: String) {
        val args = Bundle()
        args.putString("username", username)
        fragment.arguments = args
        replaceFragment(fragment, R.id.fragment_container)
    }

}