/**
 * Created by ejromeror on 3/8/17.
 */
(function ($) {
    $.fn.notify = function (options) {
        var settings = $.extend(true, {
            easeIn: {duration: 1000, effect: 'swing'},
            easeOut: {duration: 1000, effect: 'swing'},
            template: {
                info: {icon: 'glyphicon glyphicon-info-sign', 'class': 'alert-info'},
                success: {icon: 'glyphicon glyphicon-ok-sign', 'class': 'alert-success'},
                warning: {icon: 'glyphicon glyphicon-exclamation-sign', 'class': 'alert-warning'},
                error: {icon: 'glyphicon glyphicon-remove-sign', 'class': 'alert-danger'}
            },
            hasIcon: true,
            timeOut: 6000,
            showed: null,
            hidden: null
        }, options);

        var Notify = function (_container) {
            var $container = _container;

            var createNotify = function (type, message) {
                var $icon = $('<i></i>'), $text = $('<p></p>').html(message), $message = $('<div></div>');
                var template = settings.template[type] ? settings.template[type] : settings.template.info;

                if (settings.hasIcon) {
                    $icon.addClass(template['icon']);
                    $message.append($icon);
                }
                $message.append($text);
                $message.addClass(template['class']);

                return $message;
            };

            var animateNotify = function (notify, sticky) {
                var time = null;
                notify.animate({
                    right: 0
                }, settings.easeIn.duration, settings.easeIn.effect);
                if (!sticky) {
                    time = setTimeout(function () {
                        notify.off('click');
                        notify.animate({
                            right: '-20em'
                        }, settings.easeOut.duration, settings.easeOut.effect, function () {
                            notify.remove();
                        });
                    }, settings.timeOut);
                }
                notify.on('click', function () {
                    if (!sticky) {
                        clearTimeout(time);
                    }
                    notify.animate({
                        right: '-20em'
                    }, settings.easeOut.duration, settings.easeOut.effect, function () {
                        notify.remove();
                    });
                });
            };

            this.msg = function (msg) {
                var notify = createNotify(msg.type || '', msg.text || '');
                $container.append(notify);
                animateNotify(notify);
            };

            this.stick = function (msg) {
                var notify = createNotify(msg.type || '', msg.text || '');
                $container.append(notify);
                animateNotify(notify, true);
            }
        };

        this.each(function () {
            var $this = $(this);
            $this.data('notify', new Notify($this));
        });

        return this;
    }
})(jQuery);
