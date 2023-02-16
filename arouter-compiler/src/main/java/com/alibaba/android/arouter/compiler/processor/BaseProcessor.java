package com.alibaba.android.arouter.compiler.processor;

import static com.alibaba.android.arouter.compiler.utils.Consts.KEY_GENERATE_DOC_NAME;
import static com.alibaba.android.arouter.compiler.utils.Consts.KEY_MODULE_NAME;
import static com.alibaba.android.arouter.compiler.utils.Consts.NO_MODULE_NAME_TIPS;
import static com.alibaba.android.arouter.compiler.utils.Consts.VALUE_ENABLE;

import com.alibaba.android.arouter.compiler.utils.Logger;
import com.alibaba.android.arouter.compiler.utils.TypeUtils;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Base Processor
 *
 * @author zhilong [Contact me.](mailto:zhilong.lzl@alibaba-inc.com)
 * @version 1.0
 * @since 2019-03-01 12:31
 */
public abstract class BaseProcessor extends AbstractProcessor {
    /**
     * 文件生成器
     */
    Filer mFiler;
    Logger logger;
    /**
     * 类信息工具类
     */
    Types types;
    /**
     * 节点工具类（类、函数、属性都是节点）
     */
    Elements elementUtils;
    TypeUtils typeUtils;
    // Module name, maybe its 'app' or others
    String moduleName = null;
    // If need generate router doc
    boolean generateDoc;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        mFiler = processingEnv.getFiler();
        types = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        typeUtils = new TypeUtils(types, elementUtils);
        logger = new Logger(processingEnv.getMessager());

        // Attempt to get user configuration [moduleName]
        Map<String, String> options = processingEnv.getOptions();
        if (MapUtils.isNotEmpty(options)) {
            moduleName = options.get(KEY_MODULE_NAME);
            generateDoc = VALUE_ENABLE.equals(options.get(KEY_GENERATE_DOC_NAME));
        }

        if (StringUtils.isNotEmpty(moduleName)) {
            moduleName = moduleName.replaceAll("[^0-9a-zA-Z_]+", "");

            logger.info("The user has configuration the module name, it was [" + moduleName + "]");
        } else {
            logger.error(NO_MODULE_NAME_TIPS);
            throw new RuntimeException("ARouter::Compiler >>> No module name, for more information, look at gradle log.");
        }
    }
    /**
     * 声明注解处理器生成java代码规则，在这里写你的扫描、评估和处理注解的代码，以及生成Java文件。
     *
     * @param set              支持处理的注解集合。
     * @param roundEnvironment 表示当前或是之前的运行环境,可以通过该对象查找指定注解下的节点信息。
     * @return 如果返回 true，则这些注解已处理，后续 Processor 无需再处理它们；如果返回 false，则这些注解未处理并且可能要求后续 Processor 处理它们。
     */
//    @Override
//    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        return false;
//    }

    /**
     * 返回一个当前注解处理器所有支持的注解的集合。当前注解处理器需要处理哪种注解就加入那种注解。如果类型符合，就会调用process（）方法。
     * 等同于注解方式：@SupportedAnnotationTypes(ANNOTATION_TYPE_INTECEPTOR)
     */
//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        return super.getSupportedAnnotationTypes();
//    }

    /**
     * 需要通过那个版本的jdk来进行编译
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 接收外来传入的参数，最常用的形式就是在Gradle里`javaCompileOptions`
     */
    @Override
    public Set<String> getSupportedOptions() {
        return new HashSet<String>() {{
            this.add(KEY_MODULE_NAME);
            this.add(KEY_GENERATE_DOC_NAME);
        }};
    }
}
