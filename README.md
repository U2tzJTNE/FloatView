# GlobalGray

[![](https://jitpack.io/v/U2tzJTNE/FloatView.svg)](https://jitpack.io/#U2tzJTNE/FloatView)

一个应用内的悬浮view,无需任何权限.

## 依赖

在你项目的build.gradle文件中添加jitpack的仓库

```groovy
allprojects {
	repositories {
		...
        maven { url 'https://jitpack.io' }
    }
}
```

然后在你要使用的模块中添加如下依赖：

```groovy
dependencies {
    implementation 'com.github.U2tzJTNE:FloatView:Tag'
}
```

## 代码示例

Application的onCreate中调用：

```java
FloatView.with(this)
                .setIcon(R.drawable.ic_float_view)//设置图片资源
                .setSize(50, 50)//设置view的尺寸
                .setDefaultPosition(0, 100)//默认显示的位置
                .setEdgeMargin(5)//设置贴边的间距
                .setUnit(Unit.DP)//view尺寸的单位
                .isAutoEdge(true)//是否自动贴边
                .setFilter(true, SecondActivity.class)//设置过滤器 true代表显示 后面的参数可以为多个
                .setViewListener(new ViewStateListener() {//view点击事件
                    @Override
                    public void onClick(Activity activity) {
                        Toast.makeText(App.this, "我被点击了: "
                                + activity.getLocalClassName(), Toast.LENGTH_SHORT).show();
                    }
                }).build();
```