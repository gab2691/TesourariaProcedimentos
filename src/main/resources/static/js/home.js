$(document).ready(function () {
    fileList = [];
    imageList = [];

    $('.modalFullProced').css('paddingLeft' , '0');

    $(function () {
        $('.ipt-data').datepicker({
            dateFormat: 'dd/mm/yy',
        });
    });

    var initLoadingBar = function(){
        $('.first').circleProgress({
            value: 0,
            value: 0.35,
            fill: {
                gradient: ['#ea7252', '#fd0b42']
            },
            size: 100.0,
            thickness: 'auto',
            emptyFill: 'rgba(0, 0, 0, .1)',
            animationStartValue: 0,
            lineCap:'butt',
        });

    };

    var midleLoadingBar = function () {
        $('.first').circleProgress({
            value: 0.37,
            value: 0.76,
            fill: {
                gradient: ['#ea7252', '#fd0b42']
            },
            animationStartValue: 0.37,
        });
    };

    var endLoadingBar = function(){
        $('.first').circleProgress({
            value: 0.76,
            value: 1,
            fill: {
                gradient: ['#ea7252', '#fd0b42']
            },
            animationStartValue: 0.76,
        });
    };

    var toggleBackGroound = function(){
        $('.overlay').toggleClass('overlay-transition');

        $('.first').toggleClass('first-transition');
    };

    var clearFieldsForm = function(){
        $('#sistemaSelect').val('');
        $('.ipt-key').val('');
        $('.ipt-data').val('');
        $('.ipt-ticket').val('');
        $('#fileToUpload').val('');
        $('#printToUpload').val('');
        $('.any-files').text($('#fileToUpload')[0].files.length);
        $('.image-files').text($('#printToUpload')[0].files.length);
        $('.ipt-job').val('');
        $('.textArea-proced').val('');
        fileList = [];
        imageList = [];
    };

    $('.btn-searche').click(function () {
        var formData = new FormData();
        $('.overlay').css('z-index',  '1050');
        var sistema = $('#sistemaSelect').val();
        var chave = $('.ipt-key').val();
        var data = $('.ipt-data').val();
        var ticket = $('.ipt-ticket').val();
        var job = $('.ipt-job').val();
        var desc = $(".textArea-proced").val();
        toggleBackGroound();
        initLoadingBar();


        if(imageList.length == 0) {
            for (var i = 0; i < $('#printToUpload')[0].files.length; i++) {
                formData.append("image", $('#printToUpload')[0].files[i]);
            }
        }

        if(imageList.length > 0){
            for(var i=0; i< imageList.length; i++){
                formData.append("image", imageList[i]);
            }
        }

        formData.append('procedimentos', new Blob([JSON.stringify({
                sistemas : sistema,
                palavraChave : chave,
                dataProcedimento : data,
                ticket : ticket,
                malhaBatch : job,
                descProcedimento : desc
            })],
            {
                type : "application/json"
            }))


        $.ajax({
            method: "POST",
            url: "/searchProcedimentos",
            processData: false,
            contentType: false,
            cache: false,
            data: formData,
            success: function (data,textStatus,jqXHR) {
                if(jqXHR.status == 204){
                    endLoadingBar();
                    setTimeout(function () {
                        toggleBackGroound();
                        $('.modal-noDataFound').modal('show');
                        $('.overlay').css('z-index', '-9');
                    }, 1000);
                    return;
                }
                $('.modal-list-proced').find('.modal-content').html(data);
                endLoadingBar();

                setTimeout(function () {
                    toggleBackGroound();
                }, 1000);

                setTimeout(function () {
                    $('.modal-list-proced').modal('show');
                }, 1200);

            },
            error: function () {
                endLoadingBar();
                setTimeout(function () {
                    toggleBackGroound();
                }, 2000);
            }
        })
    });

    $('.btn-save').click(function () {

        var sistema = $('#sistemaSelect').val();
        var chave = $('.ipt-key').val();
        var data = $('.ipt-data').val();
        var ticket = $('.ipt-ticket').val();
        var job = $('.ipt-job').val();
        var desc = $(".textArea-proced").val();

        if(sistema == "" || chave == "" || data == "" || desc == ''){
            return;
        }
        $('.overlay').css('z-index', '1050');
        toggleBackGroound();
        initLoadingBar();

        var formData = new FormData();
        var file = new Array();

        if(fileList.length >0){
            for (var i = 0; i < fileList.length; i++) {
                formData.append("doc", fileList[i]);
            }
        }

        if(fileList.length == 0){
            for (var i = 0; i < $('#fileToUpload')[0].files.length; i++) {
                formData.append("doc", $('#fileToUpload')[0].files[i]);
            }
        }

        if(imageList.length == 0) {
            for (var i = 0; i < $('#printToUpload')[0].files.length; i++) {
                formData.append("image", $('#printToUpload')[0].files[i]);
            }
        }

        if(imageList.length > 0){
            for(var i=0; i< imageList.length; i++){
                formData.append("image", imageList[i]);
            }
        }

        formData.append('procedimentos', new Blob([JSON.stringify({
            sistemas : sistema,
            palavraChave : chave,
            dataProcedimento : data,
            ticket : ticket,
            malhaBatch : job,
            descProcedimento : desc
            })],
            {
                type : "application/json"
            }))

        midleLoadingBar();
        $.ajax({

            method: "POST",
            url: "/insertProcedimento",
            processData: false,
            contentType: false,
            cache: false,
            data: formData,
            success: function (data, xhr, statusText) {
                clearFieldsForm();
                endLoadingBar();
                setTimeout(function () {
                    toggleBackGroound();
                    $('.overlay').css('z-index', '-9');
                }, 1500);

                setTimeout(function () {
                    $('.modal-insert-ok').modal('show');
                }, 2000);
            },
            error: function(data){
                clearFieldsForm();
                endLoadingBar();
                setTimeout(function () {
                    toggleBackGroound();
                    $('.overlay').css('z-index', '-9');
                }, 1500);

                setTimeout(function () {
                    $('.modal-insert-error').modal('show');
                }, 2000);
            }
        })
    });

    $('#fileToUpload').change(function () {
        $('.any-files').text($(this)[0].files.length);
    });

    $('#printToUpload').change(function () {
        $('.image-files').text($(this)[0].files.length);
    });
    
    $('.any-files').click(function () {
        $('.modal-preview-arqui').find('.modal-body').empty();
        var files = $('#fileToUpload')[0].files;

        if(fileList.length > 0){
            for(var x =0; x < fileList.length; x++) {

                var extens = fileList[x].name.split('.').pop();

                if(extens == 'pdf'){
                    $('.modal-preview-arqui').find('.modal-body').append(
                        '<div class="container-preview">' +
                        '<span class="icon-cancel-circle"></span>' +
                        '<div class="modal-preview-arch d-flex flex-column align-items-center">' +
                        '<span class="icon-document-file-pdf"></span>'+
                        '<p class="name-file-arch">' +fileList[x].name+ '</p>' +
                        '</div>' +
                        '</div>'
                    )

                }

                if(extens == 'sql'){
                    $('.modal-preview-arqui').find('.modal-body').append(
                        '<div class="container-preview">' +
                        '<span class="icon-cancel-circle"></span>' +
                        '<div class="modal-preview-arch d-flex flex-column align-items-center">' +
                        '<span class="icon-document-file-sql"></span>'+
                        '<p class="name-file-arch">' +fileList[x].name+ '</p>' +
                        '</div>' +
                        '</div>'

                    )

                }

                if(extens == 'docx'){
                    $('.modal-preview-arqui').find('.modal-body').append(
                        '<div class="container-preview">' +
                        '<span class="icon-cancel-circle"></span>' +
                        '<div class="modal-preview-arch d-flex flex-column align-items-center">' +
                        '<span class="icon-document-file-docx"></span>'+
                        '<p class="name-file-arch">' +fileList[x].name+ '</p>' +
                        '</div>' +
                        '</div>'
                    )

                }

                if(extens == 'xlsx'){
                    $('.modal-preview-arqui').find('.modal-body').append(
                        '<div class="container-preview">' +
                        '<span class="icon-cancel-circle"></span>' +
                        '<div class="modal-preview-arch d-flex flex-column align-items-center">' +

                        '<span class="icon-document-file-xlsx"></span>'+
                        '<p class="name-file-arch">' +fileList[x].name+ '</p>' +
                        '</div>' +
                        '</div>'
                    )

                }

                if(extens == 'txt'){
                    $('.modal-preview-arqui').find('.modal-body').append(
                        '<div class="container-preview">' +
                        '<span class="icon-cancel-circle"></span>' +
                        '<div class="modal-preview-arch d-flex flex-column align-items-center">' +

                        '<span class="icon-document-file-txt"></span>'+
                        '<p class="name-file-arch">' +fileList[x].name+ '</p>' +
                        '</div>' +
                        '</div>'
                    )

                }

                if(extens == 'zip'){
                    $('.modal-preview-arqui').find('.modal-body').append(
                        '<div class="container-preview">' +
                        '<span class="icon-cancel-circle"></span>' +
                        '<div class="modal-preview-arch d-flex flex-column align-items-center">' +

                        '<span class="icon-document-file-zip"></span>'+
                        '<p class="name-file-arch">' +fileList[x].name+ '</p>' +
                        '</div>' +
                        '</div>'
                    )

                }

                if(extens == 'msg'){
                    $('.modal-preview-arqui').find('.modal-body').append(
                        '<div class="container-preview">' +
                        '<span class="icon-cancel-circle"></span>' +
                        '<div class="modal-preview-arch d-flex flex-column align-items-center">' +

                        '<span class="icon-inbox-document-text"></span>'+
                        '<p class="name-file-arch">' +fileList[x].name+ '</p>' +
                        '</div>' +
                        '</div>'
                    )

                }

                if(extens !='pdf' && extens != 'sql' && extens != "txt" && extens != "docx" && extens != "msg" && extens != "zip"
                    && extens != "xlsx"){
                    $('.modal-preview-arqui').find('.modal-body').append(
                        '<div class="container-preview">' +
                        '<span class="icon-cancel-circle"></span>' +
                        '<div class="modal-preview-arch d-flex flex-column align-items-center">' +

                        '<span class="icon-document-file-numbers"></span>'+
                        '<p class="name-file-arch">' +fileList[x].name+ '</p>' +
                        '</div>' +
                        '</div>'
                    )
                }
            }
        }
        if(fileList.length == 0){
            for (var x = 0; x < files.length; x++) {
                var extens = files[x].name.split('.').pop();

                if (extens == 'pdf') {
                    $('.modal-preview-arqui').find('.modal-body').append(
                        '<div class="container-preview">' +
                        '<span class="icon-cancel-circle"></span>' +
                        '<div class="modal-preview-arch d-flex flex-column align-items-center">' +
                        '<span class="icon-document-file-pdf"></span>' +
                        '<p class="name-file-arch">' + files[x].name + '</p>' +
                        '</div>' +
                        '</div>'
                    )

                }

                if (extens == 'sql') {
                    $('.modal-preview-arqui').find('.modal-body').append(
                        '<div class="container-preview">' +
                        '<span class="icon-cancel-circle"></span>' +
                        '<div class="modal-preview-arch d-flex flex-column align-items-center">' +
                        '<span class="icon-document-file-sql"></span>' +
                        '<p class="name-file-arch">' + files[x].name + '</p>' +
                        '</div>' +
                        '</div>'
                    )

                }

                if (extens == 'docx') {
                    $('.modal-preview-arqui').find('.modal-body').append(
                        '<div class="container-preview">' +
                        '<span class="icon-cancel-circle"></span>' +
                        '<div class="modal-preview-arch d-flex flex-column align-items-center">' +
                        '<span class="icon-document-file-docx"></span>' +
                        '<p class="name-file-arch">' + files[x].name + '</p>' +
                        '</div>' +
                        '</div>'
                    )

                }

                if (extens == 'xlsx') {
                    $('.modal-preview-arqui').find('.modal-body').append(
                        '<div class="container-preview">' +
                        '<span class="icon-cancel-circle"></span>' +
                        '<div class="modal-preview-arch d-flex flex-column align-items-center">' +

                        '<span class="icon-document-file-xlsx"></span>' +
                        '<p class="name-file-arch">' + files[x].name + '</p>' +
                        '</div>' +
                        '</div>'
                    )

                }

                if (extens == 'txt') {
                    $('.modal-preview-arqui').find('.modal-body').append(
                        '<div class="container-preview">' +
                        '<span class="icon-cancel-circle"></span>' +
                        '<div class="modal-preview-arch d-flex flex-column align-items-center">' +

                        '<span class="icon-document-file-txt"></span>' +
                        '<p class="name-file-arch">' + files[x].name + '</p>' +
                        '</div>' +
                        '</div>'
                    )

                }

                if (extens == 'zip') {
                    $('.modal-preview-arqui').find('.modal-body').append(
                        '<div class="container-preview">' +
                        '<span class="icon-cancel-circle"></span>' +
                        '<div class="modal-preview-arch d-flex flex-column align-items-center">' +

                        '<span class="icon-document-file-zip"></span>' +
                        '<p class="name-file-arch">' + files[x].name + '</p>' +
                        '</div>' +
                        '</div>'
                    )

                }

                if (extens == 'msg') {
                    $('.modal-preview-arqui').find('.modal-body').append(
                        '<div class="container-preview">' +
                        '<span class="icon-cancel-circle"></span>' +
                        '<div class="modal-preview-arch d-flex flex-column align-items-center">' +

                        '<span class="icon-inbox-document-text"></span>' +
                        '<p class="name-file-arch">' + files[x].name + '</p>' +
                        '</div>' +
                        '</div>'
                    )

                }

                if (extens != 'pdf' && extens != 'sql' && extens != "txt" && extens != "docx" && extens != "msg" && extens != "zip"
                    && extens != "xlsx") {
                    $('.modal-preview-arqui').find('.modal-body').append(
                        '<div class="container-preview">' +
                        '<span class="icon-cancel-circle"></span>' +
                        '<div class="modal-preview-arch d-flex flex-column align-items-center">' +

                        '<span class="icon-document-file-numbers"></span>' +
                        '<p class="name-file-arch">' + $('#fileToUpload')[0].files[x].name + '</p>' +
                        '</div>' +
                        '</div>'
                    )
                }
            }
        }

        if(files.length == 0 && fileList.length == 0){
            $('.modal-body').append( "<p>Voce não selecionou arquivos para fazer upload</p>" );
        }

        $(".modal-preview-arqui").modal('show');
    });

    $('.image-files').click(function () {
        $('.modal-body').empty();

        if(imageList.length > 0){
            for(var x =0; x < imageList.length; x++) {
                if (imageList[x].name.split('.').pop() == 'png' ||
                    imageList[x].name.split('.').pop() == 'jpg') {

                    var anyWindow = window.URL || window.webkitURL;
                    var objectUrl = anyWindow.createObjectURL(imageList[x]);


                    $('.modal-body').append(
                        '<div class="box-preview-img"><img class="img-preview" src= "' +objectUrl+'" > ' +
                        '<span class="icon-cancel-image"></span>' +
                        '<span class="name-file">'+imageList[x].name+'</span>' +
                        '</div>'
                    )
                }
            }
        }

        if(imageList.length == 0){
            for(var x =0; x < $('#printToUpload')[0].files.length; x++) {
                if ($('#printToUpload')[0].files[x].name.split('.').pop() == 'png' ||
                    $('#printToUpload')[0].files[x].name.split('.').pop() == 'jpg') {

                var anyWindow = window.URL || window.webkitURL;
                var objectUrl = anyWindow.createObjectURL($('#printToUpload')[0].files[x]);
                
                console.log($('#printToUpload')[0].files[x])
                $('.modal-body').append(
                    '<div class="box-preview-img"><img class="img-preview" src= "' +objectUrl+'" > ' +
                        '<span class="icon-cancel-image"></span>' +
                        '<span class="name-file">'+$('#printToUpload')[0].files[x].name+'</span>' +
                    '</div>'
                    )
                }
            }
        }

        if($('#printToUpload')[0].files.length == 0){
            $('.modal-body').append( "<p>Voce não selecionou arquivos para fazer upload</p>" );
        }

        $(".modal-preview-file").modal('show');
    });
})







