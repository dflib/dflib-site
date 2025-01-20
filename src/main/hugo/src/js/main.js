"use strict";

window.addEventListener("load", () => {
    // Open/Hide navigation menu in mobile
    var menuToggler = document.querySelector(".d_hmbrg");
    menuToggler.addEventListener("click", () => {
        menuToggler.classList.toggle('d_active');
        openMenu();
        if (document.body.classList.contains('o_overflow_hidden')) {
            showOverflow();
        } else {
            hideOverflow();
        }
    });
});

document.addEventListener("DOMContentLoaded", function () {
    //lazy Load img and background images
    var lazyImages = [].slice.call(document.querySelectorAll("img.lazy"));

    if ("IntersectionObserver" in window) {
        let lazyImageObserver = new IntersectionObserver(function (entries, observer) {
            entries.forEach(function (entry) {
                if (entry.isIntersecting) {
                    let lazyImage = entry.target;
                    lazyImage.removeAttribute("srcset");
                    lazyImage.classList.remove("lazy");
                    lazyImageObserver.unobserve(lazyImage);
                }
            });
        }, {
            root: null,
            rootMargin: '350px',
        });

        lazyImages.forEach(function (lazyImage) {
            lazyImageObserver.observe(lazyImage);
        });
    }
    //lazy Load img and background images
    var lazyBackgrounds = [].slice.call(document.querySelectorAll(".lazy_image_bc"));

    if ("IntersectionObserver" in window) {
        let lazyBackgroundObserver = new IntersectionObserver(function (entries, observer) {
            entries.forEach(function (entry) {
                if (entry.isIntersecting) {
                    entry.target.classList.remove("lazy_image_bc");
                    lazyBackgroundObserver.unobserve(entry.target);
                }
            });
        }, {
            root: null,
            rootMargin: '350px',
        });

        lazyBackgrounds.forEach(function (lazyBackground) {
            lazyBackgroundObserver.observe(lazyBackground);
        });
    }
});

/**
 * Open navigation menu
 */
var openMenu = () => {
    var menu = document.querySelector('.d_m_m');
    if (!menu) {
        return;
    }
    menu.classList.toggle('d_show');
}

/**
 * add overflow hidden
 */
var hideOverflow = () => {
    document.body.classList.add('o_overflow_hidden');
}

/**
 * remove overflow hidden
 */
var showOverflow = () => {
    document.body.classList.remove('o_overflow_hidden');
}