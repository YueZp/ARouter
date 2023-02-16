public interface Element extends AnnotatedConstruct {
    // 获取元素的类型，实际的对象类型
    TypeMirror asType();
    // 获取Element的类型，判断是哪种Element
    ElementKind getKind();
    // 获取修饰符，如public static final等关键字
    Set<Modifier> getModifiers();
    // 获取名字，不带包名
    Name getSimpleName();
    // 返回包含该节点的父节点，与getEnclosedElements()方法相反
    Element getEnclosingElement();
    // 返回该节点下直接包含的子节点，例如包节点下包含的类节点
    List<? extends Element> getEnclosedElements();

    boolean equals(Object var1);

    int hashCode();
 
    List<? extends AnnotationMirror> getAnnotationMirrors();

    <A extends Annotation> A getAnnotation(Class<A> var1);

    <R, P> R accept(ElementVisitor<R, P> var1, P var2);`
}