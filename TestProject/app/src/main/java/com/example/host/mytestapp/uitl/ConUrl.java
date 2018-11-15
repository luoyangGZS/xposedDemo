package com.example.host.mytestapp.uitl;

public class ConUrl {

    public static String[] imageUrl = { "http://04.imgmini.eastday.com/mobile/20180724/20180724_02d69787c0d6f6ac6765bbd469b75bea_cover_mwpm_03200403.jpg",
            "http://01.imgmini.eastday.com/mobile/20180724/20180724075423_c7794e7e26733e24f90ffe12e3512888_7_mwpm_03200403.jpg",
            "https://ext.baidu.com/api/like/v1/common/list?ids=wenda_a36cfa1fef9400cb4a585467c178b6207b80507%2Cwenda_8aaca4122e0a64ebd29c5859f06f80919f747ff%2Cwenda_22dacd9bf32b6680f6f940503e1658da8c5deb9&get_type=2&sfrom=wenda&source=wenda_level1&_=1532402065553&callback=Zepto1532402064904"
    };

    public static String[] urlBlackList = {
            "https://ext.baidu.com/api/comment/v1/comment/getlist?appid=101&sid=114551_124692_109775_123799_123733_120179_124267_123019_118896_118862_118850_118818_118794_107315_117330_117428_124603_122790_124752_124619_123984_124560_124109_114820_124612_124525_124937_123980_120260_124030_124298_110085_121265_123290_116145&cuid=&isInf=1&start=0&num=2&use_uk=1&use_list=1&is_need_at=1&order=12&thread_id=1038000007854017&callback=_box_jsonp14",
            //"https://ext.baidu.com/api/like/v1/common/list?ids=wenda_a36cfa1fef9400cb4a585467c178b6207b80507%2Cwenda_8aaca4122e0a64ebd29c5859f06f80919f747ff%2Cwenda_22dacd9bf32b6680f6f940503e1658da8c5deb9&get_type=2&sfrom=wenda&source=wenda_level1&_=1532402065553&callback=Zepto1532402064904",

            "https://ext.baidu.com/api/comment/v1/comment/getlist?appid=101&sid=114551_124692_109775_123799_123733_120179_124267_123019_118896_118862_118850_118818_118794_107315_117330_117428_124603_122790_124752_124619_123984_124560_124109_114820_124612_124525_124937_123980_120260_124030_124298_110085_121265_123290_116145&cuid=&isInf=1&start=0&num=2&use_uk=1&use_list=1&is_need_at=1&order=12&thread_id=1038000007854017&callback=_box_jsonp14",
            "http://api.ksapisrv.com/rest/n/client/event/push?mod=LGE%28AOSP%20on%20HammerHead%29&lon=0&country_code=CN&did=ANDROID_5b3913b05600ead0&client_key=3c2cd3f3&app=0&net=WIFI&os=android&sig2=f4eaa69db3b78ae2e16db48cb9472d41&oc=HUAWEI&ud=0&c=HUAWEI&sys=ANDROID_6.0.1&appver=5.8.2.6462&ftt=&language=zh-cn&iuid=&lat=0&did_gt=1531914362568&ver=5.8&max_memory=192",
            "http://01.imgmini.eastday.com/mobile/20180724/20180724075423_c7794e7e26733e24f90ffe12e3512888_7_mwpm_03200403.jpg",};


    public static String[] pictureFormat ={".JIF",".GIF",".JPEG",".jpeg",".bmp",".jpg","PNG",".png",".tiff",".gif",".pcx",".tga",".exif",".fpx",".svg",".psd",".cdr",".pcd",".dxf",".ufo",".eps",".ai",".raw",".WMF",".webp"};

    public static String[] regexDemo = {
            "http://f10.baidu.com/it/u=1804840131,2501642063&fm=76",
            "http://f12.baidu.com/it/u=244311167,3114440071&fm=76",
            "https://f11.baidu.com/it/u=414523606,3145888323&fm=76",};

    /**
     *
     */                           //"(http|https)://[a-z]\\d+.baidu.com/it/u=\\d+,\\d+&fm=\\d+"
    public static String[] regex = {"(http|https)://[a-z]\\d+.baidu.com/it/u=\\d+,\\d+&fm=\\d+","(http|https)://wn.pos.baidu.com/adx.php\\?c=[\\s\\S]*"};

}
