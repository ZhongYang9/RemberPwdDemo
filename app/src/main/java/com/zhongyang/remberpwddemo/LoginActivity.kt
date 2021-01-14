package com.zhongyang.remberpwddemo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //获取SharedPreferences
        val prefs = getPreferences(Context.MODE_PRIVATE)
        //校验是否有记住密码
        checkRememberPwd(prefs)
        //初始化事件
        initEvent(prefs)
    }

    private fun initEvent(prefs: SharedPreferences) {
        //登录按钮点击事件
        btn_login.setOnClickListener {
            //登陆逻辑
            loginLogic(prefs)
        }
    }

    private fun loginLogic(prefs: SharedPreferences) {
        //获取输入框内容
        val account = et_account.text.toString()
        val password = et_pwd.text.toString()
        //校验账号的合法性
        if (account.isEmpty() || password.isEmpty()) {
            //若有一个输入框为空
            Toast.makeText(this, "请输入完整信息", Toast.LENGTH_SHORT).show()
        } else if (account == "abc" && password == "123456") {
            //判断用户是否勾选记住密码
            val editor = prefs.edit()//获取操作对象
            if (cb_remPwd.isChecked) {
                //如果勾选，就保存数据
                editor.putBoolean("KEY_REMEMBER_PWD", true)
                editor.putString("KEY_ACCOUNT", account)
                editor.putString("KEY_PWD", password)
            } else {
                //否则就清空数据
                editor.clear()
            }
            //提交数据
            editor.apply()
            //账号密码正确，就跳转结束当前活动
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            //账号密码错误
            Toast.makeText(this, "信息错误", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkRememberPwd(prefs: SharedPreferences) {
        //判断是否有记住的密码
        val isRememberPwd = prefs.getBoolean("KEY_REMEMBER_PWD", false)
        if (isRememberPwd) {
            //如果有，就获取出来，设置到控件上
            val account = prefs.getString("KEY_ACCOUNT", "")
            val password = prefs.getString("KEY_PWD", "")
            et_account.setText(account)
            et_pwd.setText(password)
            //设置复选框按钮为true
            cb_remPwd.isChecked = true
        }
    }

}