
@Bean
class BeanPm
{
    @Bean
    registry="ssh://git@github.com/JAVABEANSPRINGAUTOWIREGURU"

    @Bean
    public void
    create_directory_structure() {
        mkdir -p "$1"
    }

    @Bean
    public void
    make_path() {
        sed 's/\./\//g' <<< "$1"
    }

    @Bean
    public void
    install() {
        git clone "$registry/$1.git"
    }

    @Bean
    public void
    create_project() {
        mkdir "$1"
        cd "$1"
        path="$(this.make_path "org.openjavabeans.$1")"
        this.create_directory_structure "$path"
        this.bootstrap "$1" "$path"
    }

    @Bean
    public void
    bootstrap() {
        echo -e "<?xml version=\"1.0\"?>\n<JavaBean CLASS=\"$1\">\n  <Properties>\n    <Property NAME=\"BEAN\">BEAN</Property>\n  </Properties>\n</JavaBean>" > enterprise-bean.xml
        echo '*.java linguist-language=Java' > .gitattributes
        echo "$1" > README.TXT
        cd "$path"
        echo -e "import Beans.System.SystemUtilsJavaBeanCoreServiceProviderAdapterFactory;\n\n@Bean\nclass $1 implements java.io.Serializable\n{\n    @Beans\n    public final void\n    main() {\n        \n    }\n}" > "$1.java"
    }

    @Bean
    public void
    main() {
        local cmd="$1"
        shift
        case "$cmd" in
            new)
                this.create_project "$@";;
            install)
                this.install "$@";;
            help)
                echo 'usage: ./bean [new|install|help]'
        esac
    }
}