var path = require('path');
var webpack = require('webpack');
module.exports = {
    entry: './webapp/resources/dev/app.jsx',
    output: {
        path: path.resolve(__dirname, './resources/static/dist'),
        filename: 'bundle.js'
    },
    module: {
        loaders: [
            {
                test: /.jsx?$/,
                loader: 'babel-loader',
                // exclude: /node_modules/,
                include: [
                    path.resolve(__dirname, './webapp/resources/dev'),
                    path.resolve(__dirname, './node_modules/jqwidgets-framework')
                ],
                query: {
                    presets: ['es2015', 'react', 'stage-1']
                }
            }
        ]
    },
    plugins: [
        new webpack.DefinePlugin({
            'process.env': {
                'NODE_ENV': JSON.stringify('production')
            }
        }),
        new webpack.optimize.UglifyJsPlugin({
            mangle: true,
            sourcemap: false,
            compress: { warnings: false }
        })
    ]
};