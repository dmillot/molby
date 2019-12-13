using Molby.Models;
using Molby.Services;
using Prism;
using Prism.Commands;
using Prism.Mvvm;
using Prism.Navigation;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;

namespace Molby.ViewModels
{
    public class LevelsPageViewModel : BindableBase, IActiveAware, INavigationAware
    {
        private readonly LevelService _levelService;
        private ObservableCollection<Level> _levelsList;
        private bool _isActive;

        public event EventHandler IsActiveChanged;

        public ObservableCollection<Level> LevelsList
        {
            get { return _levelsList; }
            set { SetProperty(ref _levelsList, value); }
        }

        public bool IsActive
        {
            get { return _isActive; }
            set
            {
                _isActive = value;
                OnActiveTabChangedAsync();
            }
        }

        private async void OnActiveTabChangedAsync()
        {
            if (IsActive)
            {
                var result = await _levelService.GetLevelsList();
                LevelsList = new ObservableCollection<Level>(result);
            }
        }

        public void OnNavigatedFrom(INavigationParameters parameters)
        {
            var test = parameters;
            Console.WriteLine(parameters);
        }

        public void OnNavigatedTo(INavigationParameters parameters)
        {
            var test = parameters;
            Console.WriteLine(parameters);
        }

        public LevelsPageViewModel(LevelService levelService)
        {
            _levelService = levelService;
        }
    }
}
