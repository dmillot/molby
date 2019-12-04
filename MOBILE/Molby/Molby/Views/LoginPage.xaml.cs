using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;
using Plugin.Vibrate;
using Xamarin.Essentials;

namespace Molby.Views
{
    public partial class LoginPage : ContentPage
    {
        public LoginPage()
        {
            InitializeComponent();
            //LoaderLoginPage();
        }
        public async void LoaderLoginPage()
        {
            //Task.Run(() => { 
            //while (true)
            //{
            //    LoadIndicator.Color = Color.Red;
            //    TimeSpan.FromSeconds(3000);
            //    LoadIndicator.Color = Color.White;
            //    TimeSpan.FromSeconds(3000);
            //    LoadIndicator.Color = Color.Blue;
            //    TimeSpan.FromSeconds(3000);
            //}
            //});
        }

        private void btLoginClicked(object sender, System.EventArgs e)
        {
            Vibration.Vibrate();
            var duration = TimeSpan.FromSeconds(1);
            Vibration.Vibrate(duration);
            Navigation.PushAsync(new MainPage());
        }
    }
}
