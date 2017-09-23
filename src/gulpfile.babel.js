var gulp = require('gulp');
var browserify = require('browserify');
var babelify = require('babelify');
var source = require('vinyl-source-stream');

gulp.task('build', function () {
    return browserify({entries: './js/app.jsx', extensions: ['.jsx'], debug: true})
        .transform(babelify.configure({presets: ['es2015', 'react', "stage-1"]}))
        .bundle()
        .pipe(source('bundle.js'))
        .pipe(gulp.dest('main/resources/static/dist'));
});

gulp.task('watch', ['build'], function () {
    gulp.watch('js/**/*.jsx', ['build']);
});

gulp.task('default', ['watch']);