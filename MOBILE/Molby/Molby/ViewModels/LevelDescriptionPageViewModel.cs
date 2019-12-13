using Molby.Models;
using Molby.Services;
using Prism;
using Prism.Commands;
using Prism.Mvvm;
using Prism.Navigation;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Molby.ViewModels
{
    public class LevelDescriptionPageViewModel : BindableBase, INavigatedAware
    {
        private readonly LevelService _levelService;
        private Level _userLevel;
        public LevelDescriptionPageViewModel(LevelService levelService)
        {
            _levelService = levelService;
        }

        public Level UserLevel { get { return _userLevel; } set { SetProperty(ref _userLevel, value); } }

        public void OnNavigatedFrom(INavigationParameters parameters)
        {
            throw new NotImplementedException();
        }

        public void OnNavigatingTo(INavigationParameters parameters)
        {
            Console.Write("test");
        }


        public async void GetInfo(int id)
        {
            var result = await _levelService.GetLevelById(id);
            UserLevel = result;
        }

        public void OnNavigatedTo(INavigationParameters parameters)
        {
            throw new NotImplementedException();
        }
    }
}
