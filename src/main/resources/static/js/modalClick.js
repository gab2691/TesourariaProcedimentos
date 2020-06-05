listDocsToDelet = [];
ListImagesToDelet = [];

ListDocsToUpload = [];
ListImagesToUpload = [];

var toggleBackGroound = function(){
    $('.overlay-procd').toggleClass('overlay-transition-procd');

    $('.first-procd').toggleClass('first-transition-procd');
};

var initLoadingBarDoc = function(){
    $('.first-procd').circleProgress({
        value: 0,
        value: 0.76,
        fill: {
            gradient: ['#ea7252', '#fd0b42']
        },
        size: 100.0,
        thickness: 'auto',
        emptyFill: 'rgba(0, 0, 0, .1)',
        animationStartValue: 0.0,
        lineCap:'butt',
    });

};

var midleLoadingBar = function () {
    $('.first').circleProgress({
        value: 0.35,
        value: 0.76,
        animationStartValue: 0.35,
    });
};

var endLoadingBarDoc = function(){
    $('.first-procd').circleProgress({
        value: 0.76,
        value: 1,
        fill: {
            gradient: ['#ea7252', '#fd0b42']
        },
        size: 100.0,
        thickness: 'auto',
        emptyFill: 'rgba(0, 0, 0, .1)',
        animationStartValue: 0.76,
        lineCap:'butt',
    });
};

$('body').on('click', '.icon-cancel-image', function () {
    $(this).parent().fadeOut().remove();
    var files ='';
    var name = $(this).parent().find('.name-file').text();

    if(imageList.length == 0){
        files = $('#printToUpload')[0].files;
    }

    if(imageList.length > 0){
        files = imageList;
        imageList = [];
    }

    for(var x = 0; x < files.length; x++){
        if(name != files[x].name){
            if(imageList.length == 0){
                imageList.push(files[x])
            }
            for(var y = 0; y < imageList.length; y++) {
                if (files[x].name != imageList[y].name && y + 1 == imageList.length) {
                    imageList.push(files[x]);
                }
            }
        }
    }
    if(imageList.length == 0) $('#printToUpload').val('');
    $('.image-files').text(imageList.length);
});

$('body').on('click', '.icon-cancel-circle', function () {
    $(this).parent().fadeOut().remove();
    var files ='';
    var name = $(this).parent().find('.modal-preview-arch').find('.name-file-arch').text();

    if(fileList.length == 0){
        files = $('#fileToUpload')[0].files;
    }

    if(fileList.length > 0){
        files = fileList;
        fileList = [];
    }

    for(var x = 0; x < files.length; x++){
        if(name != files[x].name){
            if(fileList.length == 0){
                fileList.push(files[x])
            }
            for(var y = 0; y < fileList.length; y++) {
                if (files[x].name != fileList[y].name && y + 1 == fileList.length) {
                    fileList.push(files[x]);
                }
            }
        }
    }
    if(fileList.length == 0) $('#fileToUpload').val('');
    $('.any-files').text(fileList.length);
});

headerProcd = ''
$('body').on('click', '.modal-header-procd', function () {
    var id = $(this).find('.procdId').val();
    console.log(id)
    headerProcd = $(this);
    $.ajax({
        method: "POST",
        url: "/findById",
        contentType: "application/json",
        data: JSON.stringify({
            id : id
        }),
        success: function (data, xhr, statusText) {
            if (statusText.status == 200) {
                var zIndex = parseInt($('.modal:visible').css('z-index'));
                zIndex += 10;
                $('.overlay').css('z-index',  zIndex);
                $('.overlay').toggleClass('overlay-transition');
                $('.modal-details-proc').find('.modal-content').html(data);
                $('.modal-details-proc').css('z-index', '1060');
                $('.modal-details-proc').modal('show');
            }
        },
    })

});

$('body').on('click', '.image-details',function () {
    var src = $(this).attr('src');
    var zIndex = parseInt($('.modal:visible').css('z-index'));
    zIndex += 10;
    $('.overlay').css('z-index',  zIndex);
    $('.modal-full-image').css('z-index', '1070');
    $('.modal-full-image').find('.image-full-modal').attr('src',src);
    $('.modal-full-image').modal('show');
});

$('body').on('click', '.alter-procd', function () {
    toggleBackGroound();
    initLoadingBarDoc();
    $.ajax({
        method: "POST",
        url: "/updateProcd",
        contentType: "application/json",
        data:  JSON.stringify({
            id : $(this).parent().find('.id-procd-options').val(),
            sistemas : $('.sigla-proc-object').text(),
            palavraChave : $('.ipt-alter-palavra').val(),
            malhaBatch : $('.ipt-alter-job').val(),
            ticket: $('.ipt-alter-ticket').val(),
            descProcedimento : $('.dec-proc-details').val()
        }),
        success: function (data, xhr, statusText) {
            console.log(data);
            endLoadingBarDoc();
            setTimeout(function () {
                toggleBackGroound();
            }, 1300)

        },
    })

})

$('body').on('click', '.exclud-procd', function () {
    toggleBackGroound();
    initLoadingBarDoc();
    $.ajax({
        method: "POST",
        url: "/excludProcd",
        contentType: "application/json",
        data:  JSON.stringify({
            id : $(this).parent().find('.id-procd-options').val(),
        }),
        success: function (data, xhr, statusText) {
            headerProcd.removeClass('d-flex').fadeOut();
            endLoadingBarDoc();
            setTimeout(function () {
                toggleBackGroound();
                $('.modal-details-proc').modal('hide');
            }, 1300)

        },
    })
});


$('body').on('click', '.icon-inbox-document-ByID', function () {
    toggleBackGroound();
    initLoadingBarDoc();
    var name = $(this).parent().find('.name-file-arch-ByID').text();
    var id = $(this).parent().find('.procd-id-hidden').val();
    $.ajax({
        method: "POST",
        url: "/readDoc",
        data: {
            "procedimentos" : id,
            "nomeArquivo" : name
        },
        success: function (data, xhr, statusText) {
            if (statusText.status == 200) {
                endLoadingBarDoc();
                setTimeout(function () {
                    toggleBackGroound();
                }, 600);
            }
        },
    })
});


$('body').on('hover', '.box-opt-system-text', function () {
    $(this).toggleClass('change-sigla-row');
})

$('body').on('click', '.box-opt-system-text', function () {
    $('.sigla-text-change').text($(this).find('.sistem-opt-text').text());
})


contador = 1;
$('body').on('click', '.sigla-text-change, .arrow_right_rotate', function () {
    $(this).parent().parent().find('.box-sistema-alter').toggleClass('show-box-siglas');
    $('.arrow_right_rotate').toggleClass('arrow_right_change');
    contador = 0;
});

$('body').click(function () {
    if(contador != 0) {
        $(this).find('.box-sistema-alter').removeClass('show-box-siglas');
        $('.arrow_right_rotate').removeClass('arrow_right_change')
    }
    else{
        contador = 1;
    }
});

$('body').on('change', '#docsToUploadDetails', function () {
    var docs = $(this)[0].files;
    for(var x = 0; x < docs.length; x++) {
        $('.box-docs').append(
            '<div>' +
                '<div class="docx">' +
                    '<div class="container-preview container-previewById">' +
                        '<span class="icon-cancel-circle icon-cancel-byId"></span>' +
                        '<div class="modal-preview-arch modal-preview-ById d-flex flex-column align-items-center">' +
                            '<span class="icon-inbox-document-text-details icon-inbox-document-ByID"></span>' +
                            '<p class="name-file-arch name-file-arch-ByID">'+ docs[x].name +'</p>' +
                        '</div>' +
                    '</div>' +
                '</div>' +
            '</div>'
        )

        ListDocsToUpload.push(docs[x]);
    }
})


$('body').on('click', '.icon-cancel-byId', function () {
    listDocsToDelet.push($(this).parent().find('.procd-id-hidden').val());
})

$('body').on('click', '.icon-cancel-byId-images', function () {
    ListImagesToDelet.push($(this).parent().find('.id-procd-hiden').val());
})
