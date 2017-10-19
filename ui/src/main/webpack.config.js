var path = require('path');
var webpack = require('webpack');
module.exports = {
    entry: './webapp/resources/dev/app.jsx',
    output: {
        path: path.resolve(__dirname, './resources/static/dist'),
        //TODO resolve this path correctly!
        // path: path.resolve(__dirname, '../../target/classes/static/dist'),
        filename: 'bundle.js'
    },
    // resolve: {
    //     extensions: ['', '.js', '.jsx']
    // },
    module: {
        loaders: [
            {
                test: /.jsx*$/,
                loader: 'babel-loader',
                exclude: /node_modules/,
                query: {
                    presets: ['es2015', 'react', 'stage-1']
                }
            }
        ]
    },
    devtool: 'cheap-module-inline-source-map'
    // ,
    // plugins: [
    //     new webpack.DefinePlugin({
    //         'process.env': {
    //             'NODE_ENV': JSON.stringify('production')
    //         }
    //     }),
    //     new webpack.optimize.UglifyJsPlugin({
    //         mangle: true,
    //         sourcemap: false,
    //         compress: {warnings: false}
    //     })
    // ]
};