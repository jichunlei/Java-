package com.mall.Concurrency.publish;

import com.mall.Concurrency.annoations.NotRecommend;
import com.mall.Concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: xianzilei
 * @Date: 2019/7/6 08:09
 * @Description: this逸出:指在构造函数返回之前其他线程或类就持有该对象的引用,调用尚未构造完全的对象的方法可能引发令人疑惑的错误,
 */
@Slf4j
@NotThreadSafe
@NotRecommend
public class ThisEscape {
    //属性name
    private String name;

    private InnerClass InnerClass;

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public ThisEscape(String name) {
        //下面的代码未执行完，就把this对象发布出去
        this.InnerClass = new InnerClass();
        //该行代码设置类属性name
        setName(name);
    }

    public InnerClass getInnerClass() {
        return InnerClass;
    }

    //内部类
    class InnerClass {
        private String outClassName;

        public InnerClass() {
            //这行代码隐藏了this，但此时this并未完全初始化完成，所以会有潜在的风险
            outClassName = getName();
        }

        public String getOutClassName() {
            return outClassName;
        }
    }

    public static void main(String[] args) {
        ThisEscape outClass = new ThisEscape("outClassName");
        String outClassName = outClass.getInnerClass().getOutClassName();
        log.info("outClassName:{}", outClassName);
        //结果返回null
    }
}
