# CoolapkSDK
第三方酷安SDK，目前实现了第三方登录和查看用户/动态详情  
[![](https://jitpack.io/v/w568w/CoolapkSDK.svg)](https://jitpack.io/#w568w/CoolapkSDK)
# Usage
## Step 1 
添加以下内容到你的项目build.gradle中:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}
```
添加以下内容到你的模块build.gradle中:
```
dependencies {
	        compile 'com.github.w568w:CoolapkSDK:-SNAPSHOT'
}
```
## Step 2
使用下面的代码来登录酷安:
```
		CoolapkSDK.createInstance(getApplication())
                .login(this, "username", "password", new CoolapkSDK.IUiListener() {

                    @Override
                    public void onComplete(User response) {
                        Toast.makeText(Activity.this, "Login success! uid->" + response.uid + ",fans num->" + response.fans + ",feeds num->" + response.feeds + ",bio->" + response.bio, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(Activity.this, "Uuuh,Login failed!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                });
```
That's All!更多的有趣的API都在项目的`CoolapkUtils`里，请自己去发现！
# License
`GNU GENERAL PUBLIC LICENSE Version 3`