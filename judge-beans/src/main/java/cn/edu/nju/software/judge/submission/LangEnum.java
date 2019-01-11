package cn.edu.nju.software.judge.submission;

/**
 * ////////////////////////////////////////////////////////////////////
 * //                          _ooOoo_                               //
 * //                         o8888888o                              //
 * //                         88" . "88                              //
 * //                         (| ^_^ |)                              //
 * //                         O\  =  /O                              //
 * //                      ____/`---'\____                           //
 * //                    .'  \\|     |//  `.                         //
 * //                   /  \\|||  :  |||//  \                        //
 * //                  /  _||||| -:- |||||-  \                       //
 * //                  |   | \\\  -  /// |   |                       //
 * //                  | \_|  ''\---/''  |   |                       //
 * //                  \  .-\__  `-`  ___/-. /                       //
 * //                ___`. .'  /--.--\  `. . ___                     //
 * //              ."" '<  `.___\_<|>_/___.'  >'"".                  //
 * //            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
 * //            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
 * //      ========`-.____`-.___\_____/___.-`____.-'========         //
 * //                           `=---='                              //
 * //      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
 * //            佛祖保佑       永不宕机     永无BUG                    //
 * ////////////////////////////////////////////////////////////////////
 */
public enum  LangEnum {


    C(0,"C"),
    CPP(1,"C++"),
    Pascal(2,"Pascal"),
    Java(3,"Java"),
    Ruby(4,"Ruby"),
    Bash(5,"Bash"),
    Python(6,"Python"),
    PHP(7,"PHP"),
    Perl(8,"Perl"),
    CSharp(9,"C#"),
    ObjC(10,"Obj-C"),
    FreeBasic(11,"FreeBasic"),
    Scheme(12,"Scheme"),
    Clang(13,"Clang"),
    ClangPP(14,"Clang++"),
    Lua(15,"Lua"),
    JavaScript(16,"JavaScript"),
    Go(17,"Go");


    LangEnum(int language, String dispLanguage) {
        this.language = language;
        this.dispLanguage = dispLanguage;
    }

    private int language;

    private String dispLanguage;


    public static String getDispByLang(Integer language){

        for(LangEnum langEnum : LangEnum.values()){

            if(langEnum.getLanguage() == language){
                return langEnum.dispLanguage;
            }

        }

        return null;
    }


    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public String getDispLanguage() {
        return dispLanguage;
    }

    public void setDispLanguage(String dispLanguage) {
        this.dispLanguage = dispLanguage;
    }}
