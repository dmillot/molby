using Prism.Commands;
using Prism.Mvvm;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Molby.ViewModels
{
    public class LoginPageViewModel : BindableBase
    {
        public LoginPageViewModel()
        {

        }
        //public DelegateCommand NavigateToMainPageCommand => new DelegateCommand(async () => await NavigationService.NavigateAsync("MainPage"));
        public DelegateCommand NavigateToMainPageCommand { get; }

        //private async void NavigateToMainPageCommand()
        //{
        //    _bookService.DeleteBook(_book);
        //    await _navigationService.GoBackAsync();
        //}
    }
}
