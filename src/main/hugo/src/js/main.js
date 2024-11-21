"use strict";

/**
 * A flag indicating that the user is scrolling down the page.
 * It is needed in order to track how the transition to the section occurs, there are two options:
 * 1) clicking on a navigation element (userScrolling = true; the toggleActive function is not executed )
 * 2) content scrolling (userScrolling = false; toggleActive function is executed)
 */
var userScrolling = false;

window.addEventListener(
    'load',
    () => {
        // Create a list of all headings with an id attribute
        var trackedSections = document.querySelectorAll('h2, h3');

        // Get the navigation links container
        var navLinksBox = document.querySelector('.sectlevel1');

        // Create an observer for each heading
        if(trackedSections){
            trackedSections.forEach(element => {
                createObserver(element);
            });
        }

        // Add an event listener to the navigation links container
        if(navLinksBox){
            navLinksBox.addEventListener('click', (event) => {
                event.preventDefault();
                userScrolling = true;
                var targetLink = event.target.closest('a');
                if (!targetLink) return;

                requestAnimationFrame(
                    () => {
                        var id = targetLink.getAttribute('href').slice(1);
                        var targetSection = document.getElementById(id);
                        scrollToSectionAndCheckEnd(targetSection, targetLink);
                    }
                );
            });
        }

    }
)



/**
 * Scroll to the target section and check when the scrolling ends
 * @param {HTMLElement} targetSection
 */
function scrollToSectionAndCheckEnd(targetSection, targetLink) {
    var targetPosition = targetSection.getBoundingClientRect().top + window.pageYOffset;
    window.scrollTo({ top: targetPosition, behavior: 'smooth' });

    var lastPosition = -1;
    var requestAnimationFrameId;

    var checkScrollEnd = function() {
        if (lastPosition === window.pageYOffset ||
            (window.innerHeight + window.pageYOffset) >= document.body.offsetHeight) {
            var prevActiveEls = document.querySelectorAll('.sectlevel1 a.active');
            prevActiveEls?.forEach(element => {
                element.classList.remove('active');
            });
            userScrolling = false;
            targetLink.classList.add('active');
            cancelAnimationFrame(requestAnimationFrameId); // Останавливаем проверку
        } else {
            lastPosition = window.pageYOffset;
            requestAnimationFrameId = requestAnimationFrame(checkScrollEnd);
        }
    };

    requestAnimationFrameId = requestAnimationFrame(checkScrollEnd);
}

/**
 * Create a new IntersectionObserver instance
 * @param {HTMLElement} targetElement
 */
var createObserver = (targetElement) =>{
    var observer;
    var options = {
        root: document,
        rootMargin: '-5% 0px -90% 0px',
    };
    observer = new IntersectionObserver(handleIntersect, options);
    observer.observe(targetElement);
}

/**
 * Handle the intersection of the observed element
 * @param {IntersectionObserverEntry[]} entries
 */
var handleIntersect = (entries) => {
    entries.forEach((entry) => {
        if (entry.isIntersecting && !userScrolling ) {
            requestAnimationFrame(() => toggleActive(entry));
        }
    });
};

/**
 * Switching the active position of navigation links
 * @param {IntersectionObserverEntry} entry
 */
var toggleActive = (entry) => {
    var id = entry.target.id;
    var prevActive = document.querySelector('.sectlevel1 a.active');
    var activeLink = document.querySelector(`.sectlevel1 a[href="#${id}"]`);
    prevActive?.classList.remove('active');
    activeLink?.classList.add('active');

    if(window.innerWidth > 768 && activeLink){
        activeLink.addEventListener('transitionend', () => {
            scrollToActive(activeLink);
        }, { once: true });
    }
};

/**
 * Scroll to the active navigation link
 * @param {HTMLElement} activeLink
 */
var scrollToActive = (activeLink) => {
    var navBox = document.querySelector('#toc');
    var bottomPos = activeLink.getBoundingClientRect().bottom;
    Promise.resolve(1).then(() => {
        if(bottomPos >= (navBox.offsetHeight - 50) || bottomPos <= 80){
            activeLink.scrollIntoView({ block: "center", behavior: "auto" });
        }
    });
}

window.addEventListener("load", ()=>{

    // //Open video modal
    // const modal = document.getElementById("o_video_modal");
    // const modal_opener = document.querySelector(".o_modal_open");
    // if(modal_opener){
    //   modal_opener.addEventListener("click", ()=>{
    //     open_modal(modal);
    //   });
    // }

    // //Close video modal when clicked close button
    // const modal_close = document.querySelector(".o_close");
    // modal_close.addEventListener("click", function () {
    //   close_modal(this);
    // });

    // //Close video modal when clicked outside the container
    // const modal_container = modal.querySelector('.d_cntnr');
    // modal.addEventListener('click', event => {
    //   const isClickInside = modal_container.contains(event.target)
    //   if (!isClickInside) {
    //     close_modal(modal);
    //   }
    // });

    // Open/Hide navigation menu in mobile
    const menuToggler = document.querySelector(".d_hbrg");
    menuToggler.addEventListener("click", ()=>{
        menuToggler.classList.toggle('d_active');
        openMenu();
        if(document.body.classList.contains('o_overflow_hidden')){
            showOverflow();
        }else{
            hideOverflow();
        }
    });

});

/**
 * Open navigation menu
 */
const openMenu = ()=>{
    const menu = document.querySelector('.d_m_m');
    menu.classList.toggle('d_show');
}

/**
 * add overflow hidden
 */
const hideOverflow = ()=>{
    document.body.classList.add('o_overflow_hidden');
}

/**
 * remove overflow hidden
 */
const showOverflow = ()=>{
    document.body.classList.remove('o_overflow_hidden');
}

// var youtubeVideoId = 'WSqvEdRZsuE';//ID video youtube
// var player;//youtube player

// /**
//  * Function to open madal
//  *
//  * @param {HTMLElement} modal - Modal element
//  *
//  */
// function open_modal(modal){
//   modal.classList.add('o_open');
//   hideOverflow()

//   const video_iframe = document.getElementById(youtubeVideoId);

//   if( video_iframe && video_iframe.tagName == 'IFRAME'){

//     video_iframe.classList.remove('o_hide_video');

//     const iframeWindow = video_iframe.contentWindow;

//     const message = JSON.stringify({
//       event: 'command',
//       func: 'playVideo',
//       args: ''
//     });

//     iframeWindow.postMessage(message, '*');

//   }else{
//     create_video_iframe();
//     // const play_btn = modal.querySelector('.o_play');
//     // play_btn.addEventListener("click", function () {
//     //   create_video_iframe();
//     // });
//   }
// }

// /**
//  * Function to close modal windows
//  *
//  * @param {HTMLElement} close_el - The element that was clicked on (pop up close button).
//  * @returns {void}
//  *
//  */
// function close_modal(close_el){
//   close_el.closest('.o_modal').classList.remove('o_open');
//   showOverflow()

//   stop_video();

// }

// /**
//  * Function to create iframe
//  *
//  * @returns {void}
//  *
//  */
// function create_video_iframe(){

//   player = new YT.Player(youtubeVideoId, {
//     videoId: youtubeVideoId,
//     playerVars: {
//       'autoplay': 1, // Включить автоматическое воспроизведение
//     },
//     events: {
//       'onReady': onPlayerReady,
//     }
//   });

// }

// /**
//  * Handles the 'onReady' event triggered by the YouTube Player API, signaling that the player is ready for playback.
//  *
//  * @param {Object} event The event object passed by the YouTube Player API.
//  * @returns {void}
//  *
//  * }
//  */
// function onPlayerReady(event) {
//   // Start video playback using the player's playVideo method
//   event.target.playVideo();
// }

// /**
//  * Function to stop video
//  *
//  * @returns {void}
//  *
//  */
// function stop_video(){
//   const iframeElement = document.getElementById(youtubeVideoId);
//   const iframeWindow = iframeElement.contentWindow;

//   const message = JSON.stringify({
//     event: 'command',
//     func: 'stopVideo',
//     args: ''
//   });

//   iframeWindow.postMessage(message, '*');
//   iframeElement.classList.add('o_hide_video');
// }