#include <jni.h>
#include <string>

extern "C"
{


JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_urlHome(JNIEnv *env, jclass type) {
    std::string text = "http://chayin.hyk001.com/";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_urlApi(JNIEnv *env, jclass type) {
    std::string text = "http://chayin.hyk001.com/api.php/Index/";
    return env->NewStringUTF(text.c_str());
}


JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_securityStr(JNIEnv *env, jclass type) {
    std::string text = "/from/android/keystr/key";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_login(JNIEnv *env, jclass type) {
    std::string text = "login";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_sendmsg(JNIEnv *env, jclass type) {
    std::string text = "sendmsg";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_sysinfo(JNIEnv *env, jclass type) {
    std::string text = "sysinfo";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_reg(JNIEnv *env, jclass type) {
    std::string text = "reg";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_verflogin(JNIEnv *env, jclass type) {
    std::string text = "verflogin";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_logout(JNIEnv *env, jclass type) {
    std::string text = "logout";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_repass(JNIEnv *env, jclass type) {
    std::string text = "repass";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_fpass(JNIEnv *env, jclass type) {
    std::string text = "fpass";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_checklogin(JNIEnv *env, jclass type) {
    std::string text = "checklogin";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_userinfo(JNIEnv *env, jclass type) {
    std::string text = "userinfo";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_yhq(JNIEnv *env, jclass type) {
    std::string text = "yhq";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_tz(JNIEnv *env, jclass type) {
    std::string text = "tz";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_indexdata(JNIEnv *env, jclass type) {
    std::string text = "indexdata";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_cpkj(JNIEnv *env, jclass type) {
    std::string text = "cpkj";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_goodssj(JNIEnv *env, jclass type) {
    std::string text = "goodssj";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_goodssjinfo(JNIEnv *env, jclass type) {
    std::string text = "goodssjinfo";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_goodstype(JNIEnv *env, jclass type) {
    std::string text = "goodstype";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_goods(JNIEnv *env, jclass type) {
    std::string text = "goods";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_goodsinfo(JNIEnv *env, jclass type) {
    std::string text = "goodsinfo";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_cz(JNIEnv *env, jclass type) {
    std::string text = "cz";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_czlists(JNIEnv *env, jclass type) {
    std::string text = "czlists";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_czunset(JNIEnv *env, jclass type) {
    std::string text = "czunset";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_orderssj(JNIEnv *env, jclass type) {
    std::string text = "orderssj";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_sjlists(JNIEnv *env, jclass type) {
    std::string text = "sjlists";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_sjsj(JNIEnv *env, jclass type) {
    std::string text = "sjsj";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_sjth(JNIEnv *env, jclass type) {
    std::string text = "sjth";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_sjtihuo(JNIEnv *env, jclass type) {
    std::string text = "sjtihuo";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_jflists(JNIEnv *env, jclass type) {
    std::string text = "jflists";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_orders(JNIEnv *env, jclass type) {
    std::string text = "orders";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_orderslists(JNIEnv *env, jclass type) {
    std::string text = "orderslists";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_unsetorders(JNIEnv *env, jclass type) {
    std::string text = "unsetorders";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_shorders(JNIEnv *env, jclass type) {
    std::string text = "shorders";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_goodsjftype(JNIEnv *env, jclass type) {
    std::string text = "goodsjftype";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_goodsjf(JNIEnv *env, jclass type) {
    std::string text = "goodsjf";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_goodsjfinfo(JNIEnv *env, jclass type) {
    std::string text = "goodsjfinfo";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_ordersjf(JNIEnv *env, jclass type) {
    std::string text = "ordersjf";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_ordersjflists(JNIEnv *env, jclass type) {
    std::string text = "ordersjflists";
    return env->NewStringUTF(text.c_str());
}

JNIEXPORT jstring JNICALL
Java_com_sp_shangpin_utils_InternetUtil_shordersjf(JNIEnv *env, jclass type) {
    std::string text = "shordersjf";
    return env->NewStringUTF(text.c_str());
}

}
