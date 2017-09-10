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

}
