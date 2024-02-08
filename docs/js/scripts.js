window.addEventListener("load", ()=>{
  //Open video modal
  const modal = document.getElementById("o_video_modal");
  const modal_opener = document.querySelector(".o_modal_open");
  modal_opener.addEventListener("click", ()=>{
    const video_id = 'WSqvEdRZsuE';
    if(video_id){
      open_modal(modal);
    }
  });

  //Close video modal when clicked close button
  const modal_close = document.querySelector(".o_close");
  modal_close.addEventListener("click", function () {
    close_modal(this);
  });
  
  //Close video modal when clicked outside the container
  const modal_container = modal.querySelector('.d_cntnr');
  modal.addEventListener('click', event => {
    const isClickInside = modal_container.contains(event.target)
    if (!isClickInside) {
      close_modal(modal);
    }
  });

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

var youtubeVideoId = 'WSqvEdRZsuE';//ID video youtube
var player;//youtube player

/**
 * Function to open madal
 *
 * @param {HTMLElement} modal - Modal element
 *
 */
function open_modal(modal){
  modal.classList.add('o_open');
  hideOverflow()
  
  const video_iframe = document.getElementById(youtubeVideoId);

  if( video_iframe && video_iframe.tagName == 'IFRAME'){

    video_iframe.classList.remove('o_hide_video');

    const iframeWindow = video_iframe.contentWindow;
  
    const message = JSON.stringify({
      event: 'command',
      func: 'playVideo',
      args: ''
    });
  
    iframeWindow.postMessage(message, '*');

  }else{
    create_video_iframe();
    // const play_btn = modal.querySelector('.o_play');
    // play_btn.addEventListener("click", function () {
    //   create_video_iframe();
    // });
  }
}

/**
 * Function to close modal windows
 *
 * @param {HTMLElement} close_el - The element that was clicked on (pop up close button).
 * @returns {void}
 *
 */
function close_modal(close_el){
  close_el.closest('.o_modal').classList.remove('o_open');
  showOverflow()

  stop_video();

}

/**
 * Function to create iframe
 *
 * @returns {void}
 *
 */
function create_video_iframe(){

  player = new YT.Player(youtubeVideoId, {
    videoId: youtubeVideoId,
    playerVars: {
      'autoplay': 1, // Включить автоматическое воспроизведение
    },
    events: {
      'onReady': onPlayerReady,
    }
  });

}

/**
 * Handles the 'onReady' event triggered by the YouTube Player API, signaling that the player is ready for playback.
 *
 * @param {Object} event The event object passed by the YouTube Player API.
 * @returns {void}
 *
 * }
 */
function onPlayerReady(event) {
  // Start video playback using the player's playVideo method
  event.target.playVideo();
}

/**
 * Function to stop video
 *
 * @returns {void}
 *
 */
function stop_video(){
  const iframeElement = document.getElementById(youtubeVideoId);
  const iframeWindow = iframeElement.contentWindow;

  const message = JSON.stringify({
    event: 'command',
    func: 'stopVideo',
    args: ''
  });

  iframeWindow.postMessage(message, '*');
  iframeElement.classList.add('o_hide_video');
}