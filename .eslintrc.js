module.exports = {
    root: true,
    "env": {
        "browser": true,
        "es2021": true,
        "node": true
    },
    "extends": [
        "eslint:recommended",
        'plugin:vue/essential',
        '@vue/typescript/recommended',
        "plugin:@typescript-eslint/recommended"
    ],
    "parserOptions": {
        "ecmaVersion": 12,
        "parser": "@typescript-eslint/parser",
        "sourceType": "module",
        "project": "./tsconfig.eslint.json", // 指向您的TypeScript配置
        "extraFileExtensions": [".vue"] // 支持Vue文件
    },
    "parser": "vue-eslint-parser",
    "plugins": [
        "vue",
        "@typescript-eslint"
    ],
    "rules": {
        'standard/computed-property-even-spacing': 'off',
        '@typescript-eslint/no-explicit-any': 'off',
        'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
        'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
        'space-before-function-paren': 0,
        'vue/array-bracket-spacing': 0,
        'vue/arrow-spacing': 0,
        'vue/block-spacing': 0,
        'vue/brace-style': 'error',
        'vue/camelcase': 'error',
        'vue/comma-dangle': 'error',
        'vue/component-name-in-template-casing': 'error',
        'vue/key-spacing': 0,
        'vue/match-component-file-name': 'error',
        'vue/object-curly-spacing': 0,
        'vue/max-attributes-per-line': 0,
        'padded-blocks': 0,
        'semi': 0,
        'indent': 0,
        'space-infix-ops': 0,
        'space-before-blocks': 0,
        'eqeqeq': 0,
        'vue/eqeqeq': 0,
        'object-curly-spacing': 0,
        'keyword-spacing': 0,
        'spaced-comment': 0,
        'key-spacing': 0,
        'comma-spacing': 0,
        'comma-dangle': 0,
        'space-in-parens': 0,
        'standard/object-curly-even-spacing': 0,
        // 允许在初始化时显式指定类型
        '@typescript-eslint/no-inferrable-types': [
        'error',
        {
            ignoreParameters: true, // 忽略函数参数的类型注解
            ignoreProperties: true, // 忽略类属性的类型注解
        },
        ],
        '@typescript-eslint/no-this-alias': 'warn',
        // 允许在能自动推断返回类型的地方省略返回类型
        '@typescript-eslint/explicit-module-boundary-types': 'off'
    },
    "overrides": [
    {
      files: ['vue.config.js', 'jest.config.js', 'webpack.config.js'],
      rules: {
        '@typescript-eslint/no-var-requires': 'off',
        // 其他可能需要关闭的 TypeScript 规则
        '@typescript-eslint/explicit-function-return-type': 'off',
      },
    },
  ],
};
