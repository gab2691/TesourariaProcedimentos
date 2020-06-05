$('.modal-details-proc').on('hidden.bs.modal', function(event) {
    $('.overlay').css('z-index',  '-1');
    $('.overlay').removeClass('overlay-transition');
});

$('.modal-full-image').on('hidden.bs.modal', function(event) {
    $('.overlay').css('z-index',  '1060');
    $('.overlay').removeClass('overlay-transition');
});

$('.modal-list-proced').on('hidden.bs.modal', function(event) {
    $('.overlay').css('z-index',  '-1');
    $('.overlay').removeClass('overlay-transition');
});