module.exports = {
  root: true,
  env: {
    node: true,
  },
  extends: [
    "plugin:vue/strongly-recommended",
    "eslint:recommended",
    "@vue/prettier",
    "plugin:import/recommended",
  ],
  parserOptions: {
    ecmaVersion: 2020,
  },
  rules: {
    "vue/html-closing-bracket-newline": [
      "error",
      {
        singleline: "never", // 单行禁止在右括号前换行
        multiline: "always", // 多行必须在右括号前换行
      },
    ],
    // 多行元素内容前后换行
    "vue/multiline-html-element-content-newline": [
      "error",
      {
        ignoreWhenEmpty: true,
        allowEmptyLines: false,
      },
    ],
    // 组件属性进行排序
    "vue/attributes-order": [
      "error",
      {
        order: [
          "DEFINITION",
          "LIST_RENDERING",
          "CONDITIONALS",
          "RENDER_MODIFIERS",
          "GLOBAL",
          "UNIQUE",
          "TWO_WAY_BINDING",
          "OTHER_DIRECTIVES",
          "OTHER_ATTR",
          "EVENTS",
          "CONTENT",
        ],
        alphabetical: false,
      },
    ],
    "vue/order-in-components": [
      "error",
      {
        order: [
          "el",
          "name",
          "key",
          "parent",
          "functional",
          ["delimiters", "comments"],
          ["components", "directives", "filters"],
          "extends",
          "mixins",
          ["provide", "inject"],
          "ROUTER_GUARDS",
          "layout",
          "middleware",
          "validate",
          "scrollToTop",
          "transition",
          "loading",
          "inheritAttrs",
          "model",
          ["props", "propsData"],
          "emits",
          "setup",
          "asyncData",
          "data",
          "fetch",
          "head",
          "computed",
          "watch",
          "watchQuery",
          "LIFECYCLE_HOOKS",
          "methods",
          ["template", "render"],
          "renderError",
        ],
      },
    ],
    eqeqeq: ["error", "always"], // 需要使用===和!==
    "vue/eqeqeq": ["error"], // 需要使用===和!== (template中)
    "vue/padding-line-between-blocks": ["error", "always"],
    // 禁止存在未使用的属性
    "vue/no-unused-properties": [
      "error",
      {
        groups: ["props", "data", "computed", "methods"],
        deepData: true,
      },
    ],
    // 禁止使用未定义的属性
    "vue/no-undef-properties": [
      "error",
      {
        ignores: ["/^\\$/", "/^\\_/", "/^\\m_/"],
      },
    ],
    "vue/no-v-text": ["error"], // 禁止使用v-text
    "vue/multi-word-component-names": [0], // 关闭组件名称多词
    "vue/no-empty-component-block": ["error"], // 禁止存在空组件块
    "prefer-const": "error",
    "no-var": "error",
    "no-new-object": "error",
    "object-shorthand": "error",
    // "prefer-object-spread": "error",
    "prefer-destructuring": ["error", { object: true, array: false }],
    "prefer-template": "error",
    "no-eval": "error",
    "func-style": "error",
    "no-loop-func": "error",
    "prefer-rest-params": "error",
    "no-new-func": "error",
    "no-param-reassign": ["error", { props: false }],
    "one-var": ["error", "never"],
    "no-nested-ternary": "error",
    "no-unneeded-ternary": "error",
    "import/no-mutable-exports": "error",
    "import/prefer-default-export": "error",
    "import/first": "error",
    "import/extensions": ["error", "never", { css: "always" }],
    "no-console": process.env.NODE_ENV === "production" ? "error" : "warn",
    "no-debugger": process.env.NODE_ENV === "production" ? "error" : "warn",
  },
  settings: {
    "import/resolver": {
      alias: {
        map: [["@", "./src"]],
        extensions: [".vue", ".js", ".css", ".png"],
      },
    },
  },
};
